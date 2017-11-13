//import org.quartz.*;
//import org.quartz.impl.StdSchedulerFactory;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import play.GlobalSettings;
import play.Logger;

/**
 */
public class Global extends GlobalSettings {

    @Override
    public void onStart(play.Application application) {
        super.onStart(application);
        String string = "Terminal";
        String without = "without";



	}
    
	
	@Override
    public void onStop(play.Application app) {
		super.onStop(app);
        Logger.info("ApplicationController shutdown...");
    }

}