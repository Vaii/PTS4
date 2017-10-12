package controllers;

import models.Secured;
import play.mvc.*;
import views.html.shared.message;
import views.html.site.contact;
import views.html.site.index;

public class ApplicationController extends Controller {

     /* When routers are not used,
        just navigate to the action in the URL, then it will become highlighted */

    public Result index() {
        return ok(index.render("Info Support Knowledgecentre", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
    }

    public Result contact() {
        return ok(contact.render("Contact", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
    }

    public Result customRedirect(String url) {
        return redirect(url);
    }

    public Result message(String url, String messageText) {
        return ok(message.render("Contact", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), messageText, url));
    }
}

