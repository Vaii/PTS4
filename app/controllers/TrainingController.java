package controllers;

import dal.contexts.TrainingMongoContext;
import dal.interfaces.TrainingContext;
import dal.repositories.TrainingRepository;
import models.Training;
import play.data.Form;
import play.data.FormFactory;
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
    private Form<Training> form;

    @Inject
    public TrainingController(FormFactory formFactory) {
        this.form = formFactory.form(Training.class);
    }

    public Result addtraining() {
        return ok(addtraining.render(form));
    }

    public Result submit() { // submit new training
        Form<Training> filledForm = form.bindFromRequest();
        Training newTraining = filledForm.get();

        trainingRepository.addTraining(newTraining);
        Training t = trainingRepository.getTraining(newTraining.getTrainingCode());
        return ok(submit.render(t));
    }

    public Result overview() {
        return ok(trainingoverview.render(trainingRepository.getAll()));
    }

/*    public Result trainingClicked() {
        return ok(training.render("Training"));
    }*/
}
