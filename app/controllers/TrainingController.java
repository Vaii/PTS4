package controllers;


import dal.contexts.LocationMongoContext;
import dal.contexts.TrainingMongoContext;
import dal.contexts.TuitionFormMongoContext;
import dal.repositories.TrainingRepository;
import dal.repositories.TuitionFormRepository;
import dal.repositories.LocationRepository;
import models.Location;
import models.Secured;
import models.Training;
import models.TuitionForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.account.successSignUp;
import views.html.shared.message;
import views.html.training.*;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ken on 27-9-2017.
 */
public class TrainingController extends Controller {

    private Form<TuitionForm> tuitionFormForm;
    private TuitionFormRepository tutRepo = new TuitionFormRepository(new TuitionFormMongoContext("TuitionForm"));
    TrainingRepository trainingRepository = new TrainingRepository(new TrainingMongoContext("Training"));
    LocationRepository locationRepo = new LocationRepository(new LocationMongoContext("Location"));
    private Form<Training> form;
    List<Location> locations = new ArrayList<>();

    @Inject
    public TrainingController(FormFactory formFactory) {
        this.form = formFactory.form(Training.class);
        this.tuitionFormForm = formFactory.form(TuitionForm.class);
    }

    @Security.Authenticated(Secured.class)
    public Result signUpCourse(String id) {

        if (id == null) {
            return null;
        } else {
            if (Secured.getUserInfo(ctx()).getRole() != null) {
                return ok(signUpCourseEmployee.render("Training inschrijven", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), trainingRepository.getTraining(id), tuitionFormForm));
            } else {
                Training signUpFor = trainingRepository.getTraining(id);
                signUpFor.addTrainee(Secured.getUserInfo(ctx()).get_id());
                trainingRepository.updateTraining(signUpFor);
                return ok(successSignUp.render("Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
            }
        }
    }

    public Result signUpEmployee(String id) {

        Form<TuitionForm> filledTuitionForm = tuitionFormForm.bindFromRequest();

        if (filledTuitionForm.hasErrors()) {
            return badRequest(signUpCourseEmployee.render("Training inschrijven", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), trainingRepository.getTraining(id), tuitionFormForm));
        } else {
            TuitionForm filledForm = filledTuitionForm.get();
            filledForm.setTotalCosts(filledForm.getStudyCosts() + filledForm.getAccommodationCosts() + filledForm.getExaminationFees() + filledForm.getTransportCosts() + filledForm.getExtraCosts());
            tutRepo.addForm(filledForm);
            return ok(successSignUp.render("Success", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
        }
    }

    @Security.Authenticated(Secured.class)
    public Result addtraining() {
        List<Location> locations = locationRepo.getAll();
        return ok(addtraining.render(form, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), "Add Training", locations));
    }

    @Security.Authenticated(Secured.class)
    public Result submit() { // submit new training
        Form<Training> filledForm = form.bindFromRequest();

        if (filledForm.hasErrors()) {
            return badRequest(addtraining.render(filledForm, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), "Add Training", locations));
        } else {
            Training newTraining = filledForm.get();
            trainingRepository.addTraining(newTraining);
            Training t = trainingRepository.getTraining(newTraining.getTrainingCode());
            return ok(message.render("Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), "Training " + t.getName() + " is aangemaakt", "/managetraining"));
        }
    }

    public Result overview() {
        return ok(trainingoverview.render(trainingRepository.getTrainingFrequencies() ,new ArrayList<>(), null,
                "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
    }

    public Result overviewCategory(String category) {
        if(category == null) {
            return ok(trainingoverview.render(trainingRepository.getTrainingFrequencies(),new ArrayList<>(), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
        } else {
            return ok(trainingoverview.render(trainingRepository.getTrainingFrequencies(),trainingRepository.getTrainingByCategory(category), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
        }

    }

    public Result trainingOverview(String category, String id) {
        if (id == null) {
            return ok(trainingoverview.render(trainingRepository.getTrainingFrequencies(),trainingRepository.getTrainingByCategory(category), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
        } else {
            return ok(trainingoverview.render(trainingRepository.getTrainingFrequencies(),trainingRepository.getTrainingByCategory(category), trainingRepository.getTraining(id),
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
        }
    }

    @Security.Authenticated(Secured.class)
    public Result manage() {
        return ok(managetraining.render(trainingRepository.getTrainingFrequencies(), new ArrayList<>(), locationRepo.getAll(), null, "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form));
    }

    public Result manageCategory(String category) {
        if (category == null) {
            return ok(managetraining.render(trainingRepository.getTrainingFrequencies(),new ArrayList<>(), locationRepo.getAll(), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form));
        } else {
            return ok(managetraining.render(trainingRepository.getTrainingFrequencies(),trainingRepository.getTrainingByCategory(category), locationRepo.getAll(), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form));
        }
    }

    @Security.Authenticated(Secured.class)
    public Result manageTraining(String category, String id) {
        if (id == null) {
            return ok(managetraining.render(trainingRepository.getTrainingFrequencies(),trainingRepository.getTrainingByCategory(category), locationRepo.getAll(), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form));
        } else {
            Form<Training> editForm = form.fill(trainingRepository.getTraining(id));
            return ok(managetraining.render(trainingRepository.getTrainingFrequencies(),trainingRepository.getTrainingByCategory(category), locationRepo.getAll(), trainingRepository.getTraining(id),
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), editForm));
        }
    }

    @Security.Authenticated(Secured.class)
    public Result removeTraining(String category, String id) {
        if (id == null) {
            return ok(managetraining.render(trainingRepository.getTrainingFrequencies(),trainingRepository.getTrainingByCategory(category), locationRepo.getAll(), null, "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form));
        } else {
            Training t = trainingRepository.getTraining(id);
            trainingRepository.removeTraining(t);
            return ok(removetraining.render(t, "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
        }

    }

    @Security.Authenticated(Secured.class)
    public Result edit(String category, String code) {
        Form<Training> editFrom = form.fill(trainingRepository.getTraining(code));
        if (editFrom.hasErrors()) {
            flash("danger", "Wrong values");
            return badRequest(managetraining.render(trainingRepository.getTrainingFrequencies(),trainingRepository.getTrainingByCategory(category), locationRepo.getAll(), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form));
        } else {
            Form<Training> filledForm = form.bindFromRequest();
            Training training = filledForm.get();
            training.set_id(trainingRepository.getTraining(code).get_id());
            trainingRepository.updateTraining(training);
            return ok(message.render("Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), "Training " + training.getName() + " is gewijzigd", "/managetraining"));
        }
    }
}
