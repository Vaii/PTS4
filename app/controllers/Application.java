package controllers;

import models.Secured;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

     /* When routers are not used,
        just navigate to the action in the URL, then it will become highlighted */

    public Result index() {
        return ok(index.render("Index", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
    }
}

