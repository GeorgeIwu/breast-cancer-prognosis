package models;

import com.avaje.ebean.Model;
import play.Logger;
import play.data.format.Formats;
import utils.Hash;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by chigozirim on 10/27/16.
 */@Entity
@Table(name = "user_table")
public class User extends Model {

    public static Finder<Long, User> find = new Finder<Long, User>(User.class);
    @Id
    public long id;
    @Formats.NonEmpty
    @Column(unique = true)
    public String username;
    @Formats.NonEmpty
    public String passwordHash;
    @Formats.NonEmpty
    public boolean isEnabled = false;
    public boolean isFirstLogin = true;

    /**s Authenticates the supplied username and password
     *
     * @param username the username of the user record
     * @param clearPassword the password ot the user record in plain text
     * @return models.User
     * @throws Exception
     */
    public static User authenticate(String username, String clearPassword) throws Exception {

        User user = find.where().eq("username", username).findUnique();

        if(user == null){

            Logger.debug("user not found");
        }


        if (user != null) {

            Logger.debug("models.User found {}");

            if (Hash.checkPassword(clearPassword, user.passwordHash)) {

                return user;
            }

            Logger.error("Login authentication failed for user " + username);

        }

        return null;
    }

    /**
     * Finds a user by his username
     * @param username
     * @return models.User
     */
    public static User findByUserName(String username){

        return find.where().eq("username",username).findUnique();
    }
}