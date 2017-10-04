package controllers;

import models.Secured;
import play.mvc.*;
import views.html.*;

public class ApplicationController extends Controller {

     /* When routers are not used,
        just navigate to the action in the URL, then it will become highlighted */

    public Result index() {
        return ok(index.render("Info Support Knowledgecentre", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
    }

    public Result contact() {
        return ok(contact.render("Contact", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
    }
}

