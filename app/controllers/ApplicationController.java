package controllers;

import models.*;
import models.Process;
import models.form.LoginForm;
import neuralnet.neural.networks.BasicNetwork;
import neuralnet.util.custom.CSVUploader;
import org.apache.commons.lang.RandomStringUtils;
import play.Logger;
import play.cache.Cache;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import utils.HostMaster;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/** The ApplicationController controller handles the login , logout and dashboard actions
 *
 */
public class ApplicationController extends Controller {
    public Result HOME;
    public Result DASHBOARD;

    private HostMaster hm;
    private User currentUser;


    public ApplicationController() {
        this.HOME = redirect(routes.ApplicationController.index());
        this.DASHBOARD = redirect(routes.ApplicationController.list());
    }

    private User getCurrentUser() {
        if (hm == null) {
            hm = new HostMaster();
        }
        this.currentUser = hm.getCurrentUser();
        return this.currentUser;
    }

    public Result list(){
        List<Document> documents = Document.find.all();
        return ok(views.html.list.render(documents));
    }

    public Result listData(Long id) {

        return ok(views.html.listdata.render("ok"));
    }

    public Result processData() {


        return ok();
    }
    /** This processes the {@link Document} with the id
     * GET  /process/:id
     * @param id
     * @return
     */
    public Result process(final long id){
        Document document= Document.find.byId(id);

        if(document == null){
            flash("danger", "The document record was not found");
        }
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Logger.info("Started thread to upload csv");
                    CSVUploader.fetchData(document.filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        flash("success", "Processing has commenced...");
        return redirect("/new");
    }

    /** The upload form submits to this {@link Action}
     *
     * POST     /upload
     * @return
     */
    public Result submitData() {
        Logger.info(Form.form().bindFromRequest().toString());

        Document document = Form.form(Document.class).bindFromRequest().get();
        document.user = "ADMIN";
        Logger.info(document.toString());

        // Handle file uploads
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart csvFile = body.getFile("csvFile");
        String fileName;
        String contentType;

        if (csvFile != null) {
            fileName = csvFile.getFilename();
            contentType = csvFile.getContentType();
            Logger.debug(contentType);

            // TODO Make sure of the file type uploaded

            File saveToServer = new File("csvfiles/" + new SimpleDateFormat("ddMMyyhhmmss").format(new Date())+ fileName);
            document.filePath = saveToServer.getPath();
            boolean success = new File("passports").mkdirs();
            byte[] bytes = null;
            try {
                Files.copy(csvFile.getFile().toPath(),
                        saveToServer.toPath(), new CopyOption[] {
                                StandardCopyOption.COPY_ATTRIBUTES,
                                StandardCopyOption.REPLACE_EXISTING });
            } catch (IOException e) {
                Logger.error("Error", e);
                flash("danger", "Technical Error");
                return redirect("/new");
            }
        } else {
            //User did not upload a file
            flash("danger", "File not uploaded");
            return redirect("/new");
        }
        document.save();

        flash("success", "Successfully uploaded CSV file");
        if(document.purpose.contains("Train")){
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        CSVUploader.fetchData(document.filePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            flash("success", "Successfully uploaded CSV file and training commenced");
        }
        return redirect("/new");
    }

    /** This displays the charts generated from the Document passed in
     *  this will receive some GET parameters in the query string
     * @return
     */
    public Result charts(){
        return ok(views.html.charts.render());
    }


    /** This logs out the current user and returns to the log in page
     *  URL: GET /logout
     * @return Result that loads the log in page
     */
    public Result logout() {
        Logger.debug("Logout initiated");
        currentUser = getCurrentUser();
        if (currentUser == null) {
            Logger.info("No user is logged in");
        } else {
            Cache.remove(currentUser.username + "_auth_user");
            session().clear();
            Logger.info("Cache and Session cleared");
        }
        Logger.info("user ended session");
        flash("success", "Logout Successful");
        return HOME;
    }

    /** This shows the upload form for uploading a new csv {@link Document}
     *
     * @return
     */
    public Result newData() {

        return ok(views.html.upload.render());
    }

    /** This attempts to log in the user using the details entered in the
     *  login form view.
     *
     *  URL:  POST /login
     * @return Result loading the dashboard page if the login is successful
     */
    public Result login() {
        Form<LoginForm> loginForm = Form.form(LoginForm.class).bindFromRequest();
        DynamicForm requestData = Form.form().bindFromRequest();

        if (loginForm.hasErrors()) {
            play.Logger.error("Form has error -- {}", loginForm.errorsAsJson().toString());
            return badRequest(views.html.login.render(loginForm));
        }
        session("auth_user_name", loginForm.get().username);
        session("session_id", "CMBTREL" + (new SimpleDateFormat("MMyyddHHmmss").format(new Date())) + RandomStringUtils.randomNumeric(10));
        return DASHBOARD;
    }

    /** This <code>Action</code> is called when the home page / is called
     *
     * @return Result that loads the Dashboard or the Login page
     */
    public Result index() {
        Logger.info("Application has started");

        //check if a user is logged in
        if (getCurrentUser() != null) {
            if (currentUser != null && currentUser.isEnabled) {
                return DASHBOARD;
            } else {
                play.Logger.info("User account not found or disabled");
                play.Logger.info("Clearing invalid session credentials");
                session().clear();
            }
        }

        return showLogin();
    }
    /** Shows the Login form page
     *
     * @return Result loading the Login form page
     */
    public Result showLogin() {
        Form<LoginForm> loginForm = Form.form(LoginForm.class);
        return ok(views.html.login.render(loginForm));
    }


    public Result testupload() throws Exception{

//        CSVUploader.fetchData("csvfiles/trr.csv");
        CSVUploader.fetchData("repo/wdbc.txt");
        Document document = Document.find.byId((long) 1);

        if (document != null) {
            for (Patient patient : document.patients) {
                patient.NormalizeAttributes();
            }
        }

        return ok("processed");



    }

    public Result test() throws Exception{


        Document document = Document.find.byId((long) 1);

        if (document != null) {

            Process process = new Process (21, document, null);
            process.run();
            process.save();

        }

        return ok("processed");

    }




}


