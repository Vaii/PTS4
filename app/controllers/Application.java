package controllers;

import play.mvc.*;
import views.html.*;


public class Application extends Controller {

    public static Result index() {
        return ok(index.render("s"));
    }

    public static Result location(){
        return ok (location.render());
    }

}
