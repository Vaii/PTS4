package controllers;
import models.Training;
import play.api.data.Form;
import play.mvc.*;
import views.html.*;

import javax.inject.Inject;


public class Application extends Controller {

    private Form<Training> trainingForm;

   /* @Inject
    public Application(FormFactory formFactory) {

    } */

    public static Result index() {
        return ok(index.render("s"));
    }

    public static Result addtraining() {
        return ok(addtraining.render());
    }

    public static Result submit() {
        return ok(addtraining.render());
    } /* When routers are not used,
         just navigate to the action in the URL, then it will become highlighted */

}
