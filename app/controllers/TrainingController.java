package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.trainingoverview;

/**
 * Created by Ken on 27-9-2017.
 */
public class TrainingController extends Controller{

    public Result overview() {
        return ok(trainingoverview.render());
    }
}
