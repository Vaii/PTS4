package controllers;

import models.Secured;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

/**
 * Created by Ken on 27-9-2017.
 */
public class TrainingController extends Controller{

    public Result signUpCourse(){
        return ok(signUpCourse.render("Training Inschrijven", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
    }
}
