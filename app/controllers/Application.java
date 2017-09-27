package controllers;

import models.User;
import play.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import play.data.Form;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.inject.Inject;


public class Application extends Controller {
    public static Result index() {
        return ok(index.render("s"));
    }

}
