package controllers;

import dal.contexts.TrainingMongoContext;
import dal.repositories.TrainingRepository;
import models.Training;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import javax.inject.Inject;

/**
 * Created by Ken on 27-9-2017.
 */
public class TrainingController extends Controller {

    private Form<Training> form;
    private TrainingRepository repository;
    private TrainingMongoContext context;

    @Inject
    public TrainingController(FormFactory formFactory) {
        this.form = formFactory.form(Training.class);
        this.context = new TrainingMongoContext("Training");
        this.repository = new TrainingRepository(context);
    }

    public Result addtraining() {
        return ok(addtraining.render(form));
    }

    public Result submit() {
        Form<Training> filledForm = form.bindFromRequest();
        Training newTraining = filledForm.get();
        repository.addTraining(newTraining);
        Training trinna = repository.getTraining(newTraining.getTrainingCode());
        return ok(submit.render(trinna));
    }
}
