package models;

import controllers.routes;
import dal.contexts.UserMongoContext;
import dal.repositories.UserRepository;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.Context;

/**
 * Implements authorization.
 * getUsername() and onUnauthorized override superclass methods to restrict
 * access to specific pages to logged in users.
 *
 * getUser(), isLoggedIn(), and getUserInfo() provide static helper methods so that controllers
 * can check if there is a logged in user.
 */
public class Secured extends Security.Authenticator {

    private static UserRepository userRepo = new UserRepository(new UserMongoContext("Users"));

    @Override
    public String getUsername(Context ctx){
        return ctx.session().get("email");
    }

    @Override
    public Result onUnauthorized(Context ctx){
        return redirect(controllers.routes.AccountController.login());
    }

    public static String getUser(Context ctx){
        return ctx.session().get("email");
    }

    public static boolean isLoggedIn(Context ctx){
        return (getUser(ctx) != null);
    }

    public static User getUserInfo(Context ctx){
        return userRepo.getUser(getUser(ctx));
    }
}
