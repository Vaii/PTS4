package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

/**
 * Created by Ken on 27-9-2017.
 */
public class TrainingController extends Controller{

    public static Result overview(){
        return ok(trainingoverview.render());
    }
}
