package controllers;

import play.mvc.*;
import views.html.*;

public class Application extends Controller {
     /* When routers are not used,
        just navigate to the action in the URL, then it will become highlighted */

    public Result index() {
        return ok(index.render("s"));
    }
}

