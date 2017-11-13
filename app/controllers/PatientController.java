package controllers;

import com.avaje.ebean.Expr;
import com.avaje.ebean.PagedList;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Patient;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by chigozirim on 10/26/16.
 */
public class PatientController extends Controller {

    /** This returns JSON data for jQuery datatables to use and populate the table
     * in the frontend. It receives a number of parameters from the frontend
     *
     * GET  /patientJSON
     */
    public Result patientJSON(){
        // Get parameters
        Map<String, String[]> params = request().queryString();

        //Get the column filter parameters
        Integer iTotalRecords = Patient.find.findRowCount();
        String filter = params.get("search[value]")[0];
        Integer pageSize = Integer.valueOf(params.get("length")[0]);
        Integer page = Integer.valueOf(params.get("start")[0])
                / pageSize;


        /**
         * Get sorting order and column
         */
        String sortBy = "name";
        String order = params.get("order[0][dir]")[0];

        switch (Integer.valueOf(params.get("order[0][column]")[0])) {
            case 0:
                sortBy = "id";
                break;
            case 1:
                sortBy = "patientId";
                break;
            case 2:
                sortBy = "category";
                break;
            case 3:
                sortBy = "meanRadius";
                break;
            case 4:
                sortBy = "meanArea";
                break;
            case 5:
                sortBy = "meanPerimeter";
                break;
            case 6:
                sortBy = "meanTexture";
                break;
            case 7:
                sortBy = "meanSmoothness";
                break;
            case 8:
                sortBy = "meanCompactness";
                break;
            case 9:
                sortBy = "meanConcavity";
                break;
            case 10:
                sortBy = "meanConcavePoints";
                break;
            case 11:
                sortBy = "meanSymmetry";
                break;
            case 12:
                sortBy = "meanFractalDimension";
                break;
            case 13:
                sortBy = "seRadius";
                break;
            case 14:
                sortBy = "seArea";
                break;
            case 15:
                sortBy = "sePerimeter";
                break;
            case 16:
                sortBy = "seTexture";
                break;
            case 17:
                sortBy = "seSmoothness";
                break;
            case 18:
                sortBy = "seCompactness";
                break;
            case 19:
                sortBy = "seConcavity";
                break;
            case 20:
                sortBy = "seConcavePoints";
                break;
            case 21:
                sortBy = "seSymmetry";
                break;
            case 22:
                sortBy = "seFractalDimension";
                break;
            case 23:
                sortBy = "worstRadius";
                break;
            case 24:
                sortBy = "worstArea";
                break;
            case 25:
                sortBy = "worstPerimeter";
                break;
            case 26:
                sortBy = "worstTexture";
                break;
            case 27:
                sortBy = "worstSmoothness";
                break;
            case 28:
                sortBy = "worstCompactness";
                break;
            case 29:
                sortBy = "worstConcavity";
                break;
            case 30:
                sortBy = "worstConcavePoints";
                break;
            case 31:
                sortBy = "worstSymmetry";
                break;
            case 32:
                sortBy = "worstFractalDimension";
                break;
            default:
                sortBy = "id";
                break;
        }

        //Get the page to show from db
        PagedList<Patient> patientPage = null;

        Logger.debug("sort by " + sortBy);
        Logger.info("page: " + page + " page size" + pageSize);
        patientPage = Patient.find.where().or(
                Expr.ilike("id", "%" + filter + "%"),
                Expr.or(Expr.ilike("accountName", "%" + filter + "%"),
                        Expr.or(Expr.ilike("accountNumber", "%" + filter + "%"),
                                Expr.or(Expr.ilike("depositAmount", "%" + filter + "%"),
                                        Expr.or(Expr.ilike("currencyCode", "%" + filter + "%"),
                                                Expr.or(Expr.ilike("interestRate", "%" + filter + "%"),
                                                        Expr.or(Expr.ilike("customerId", "%" + filter + "%"), Expr.ilike("fundingAccountName", "%" + filter + "%"))))
                                )))).orderBy(sortBy + " " + order + ", id " + order)
                .findPagedList(page, pageSize);


        Integer iTotalDisplayRecords = patientPage.getTotalRowCount();

        Logger.debug("records fetched " + iTotalDisplayRecords);

        /**
         * Construct the JSON to return
         */
        ObjectNode result = Json.newObject();

        result.put("recordsTotal", iTotalRecords);
        result.put("recordsFiltered", iTotalDisplayRecords);

        ArrayNode an = result.putArray("data");

        for (Patient t : patientPage.getList()) {
            ObjectNode row = Json.newObject();

            row.put("0", t.id);
            row.put("1", t.patientId);
            row.put("2", t.category);

            row.put("3", t.meanRadius);
            row.put("4", t.meanArea);
            row.put("5", t.meanPerimeter);
            row.put("6", t.meanTexture);
            row.put("7", t.meanSmoothness);
            row.put("8", t.meanCompactness);
            row.put("9", t.meanConcavity);
            row.put("10", t.meanConcavePoints);
            row.put("11", t.meanSymmetry);
            row.put("12", t.meanFractalDimension);

            row.put("13", t.seRadius);
            row.put("14", t.seArea);
            row.put("15", t.sePerimeter);
            row.put("16", t.seTexture);
            row.put("17", t.seSmoothness);
            row.put("18", t.seCompactness);
            row.put("19", t.seConcavity);
            row.put("20", t.seConcavePoints);
            row.put("21", t.seSymmetry);
            row.put("22", t.seFractalDimension);

            row.put("23", t.worstRadius);
            row.put("24", t.worstArea);
            row.put("25", t.worstPerimeter);
            row.put("26", t.worstTexture);
            row.put("27", t.worstSmoothness);
            row.put("28", t.worstCompactness);
            row.put("29", t.worstConcavity);
            row.put("30", t.worstConcavePoints);
            row.put("31", t.worstSymmetry);
            row.put("32", t.worstFractalDimension);
            an.add(row);
        }
        return ok(result);
    }
}
