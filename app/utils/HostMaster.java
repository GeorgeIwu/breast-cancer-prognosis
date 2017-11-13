package utils;

import models.User;
import play.Logger;
import play.cache.Cache;

import static play.mvc.Http.Context.Implicit.session;

/**
 * Created by wale on 2/19/15.
 */
public class HostMaster {
    private User currentUser;

    public User getCurrentUser() {
        String username = session().get("auth_user_name");
        Logger.debug("FOUND USERNAME:" + username);
        currentUser = null;

        if (username == null) {
            Logger.info("No user found in session");
        } else {
            Logger.debug("In HM doing lookup with key " + username + "_auth_user");
            currentUser = (User) Cache.get(username + "_auth_user");

            if (currentUser == null) {
                Logger.info("No user found in cache");
            }

            //Lookup the db find user by username
            User userFromDB = User.findByUserName(username);

            if (userFromDB == null) {
                Logger.error("models.User with username { " + username + " } not found in DB");
                Logger.info("Clearing session");
                session().clear();
            } else {
                //Set currentUser to one found in the DB
                currentUser = userFromDB;
                Cache.set(username + "_auth_user", currentUser);
                Logger.debug("In HM saving found user to cache with key " + username + "_auth_user");
            }
        }
        return currentUser;
    }

}
