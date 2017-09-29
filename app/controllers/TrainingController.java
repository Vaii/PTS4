package controllers;

import dal.contexts.TrainingMongoContext;
import dal.interfaces.TrainingContext;
import dal.repositories.TrainingRepository;
import models.Training;
import models.Secured;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ken on 27-9-2017.
 */
public class TrainingController extends Controller{

    TrainingRepository trainingRepository = new TrainingRepository(new TrainingMongoContext("Training"));

    public Result overview() {
        return ok(trainingoverview.render(trainingRepository.getAll()));
    }

/*    public Result trainingClicked() {
        return ok(training.render("Training"));
    }*/
    public Result signUpCourse(){
        return ok(signUpCourse.render("Training Inschrijven", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
    }
}
