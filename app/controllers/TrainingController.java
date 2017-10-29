package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dal.contexts.*;
import dal.repositories.*;
import models.*;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.account.successSignUp;
import views.html.shared.message;
import views.html.training.*;
import javax.inject.Inject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Ken on 27-9-2017.
 */
public class TrainingController extends Controller {

    private Form<TuitionForm> tuitionFormForm;
    private TuitionFormRepository tutRepo = new TuitionFormRepository(new TuitionFormMongoContext("TuitionForm"));
    TrainingRepository trainingRepository = new TrainingRepository(new TrainingMongoContext("Training2"));
    LocationRepository locationRepo = new LocationRepository(new LocationMongoContext("Location"));
    UserRepository userRepo = new UserRepository(new UserMongoContext("User"));
    DateTimeRepository dateRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));

    private FormFactory formFactory;

    Form<Training> form;
    List<Location> locations = new ArrayList<>();
    List<User>teachers = new ArrayList<>();

    @Inject
    public TrainingController(FormFactory formFactory) {
        this.formFactory = formFactory;
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
        List<User> teachers = userRepo.getAllTeachers();

        JsonNode locationJson = Json.toJson(locations);
        JsonNode teacherJson = Json.toJson(teachers);

        return ok(addtraining.render(form, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), "Add Training", locations, teachers, locationJson, teacherJson));
    }

    @Security.Authenticated(Secured.class)
    public Result submit() throws ParseException { // submit new training
        DynamicForm trainingData = formFactory.form().bindFromRequest();

        Map<String, String> baseValues = mapValuesFromRequest(trainingData);

        List<String> dates = getValues(trainingData, "Date");
        List<String> locationIDs = getValues(trainingData, "Location");
        List<String> teacherIDs = getValues(trainingData, "Teacher");

        if (form.hasErrors()) {
            return badRequest(addtraining.render(form, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), "Add Training", locationRepo.getAll(), userRepo.getAllTeachers(), null, null));
        } else {
            Training training = form.bind(baseValues).get();

            List<String> dateIDs = getDateIDs(dates, locationIDs, teacherIDs);
            
            training.setDateIDs(dateIDs);

            trainingRepository.addTraining(training);
            Training t = trainingRepository.getTraining(training.getTrainingCode());
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
        return ok(managetraining.render(trainingRepository.getTrainingFrequencies(), userRepo.getAllTeachers(), new ArrayList<>(), locationRepo.getAll(), null, "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null));
    }

    public Result manageCategory(String category) {
        if (category == null) {
            return ok(managetraining.render(trainingRepository.getTrainingFrequencies(), userRepo.getAllTeachers(), new ArrayList<>(), locationRepo.getAll(), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null));
        } else {
            return ok(managetraining.render(trainingRepository.getTrainingFrequencies(), userRepo.getAllTeachers(), trainingRepository.getTrainingByCategory(category), locationRepo.getAll(), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null));
        }
    }

    @Security.Authenticated(Secured.class)
    public Result manageTraining(String category, String id) {
        if (id == null) {
            return ok(managetraining.render(trainingRepository.getTrainingFrequencies(), userRepo.getAllTeachers(), trainingRepository.getTrainingByCategory(category), locationRepo.getAll(), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null));
        } else {
            Training t = trainingRepository.getTraining(id);
            List<DateTime> dateTimes = new ArrayList<>();

            for(String dateTime : t.getDateIDs()) {
                DateTime d = dateRepo.getDateTime(dateTime);
                dateTimes.add(d);
            }

            Form<Training> editForm = form.fill(trainingRepository.getTraining(id));
            return ok(managetraining.render(trainingRepository.getTrainingFrequencies(), userRepo.getAllTeachers(),trainingRepository.getTrainingByCategory(category), locationRepo.getAll(), trainingRepository.getTraining(id),
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), editForm, dateTimes));
        }
    }

    @Security.Authenticated(Secured.class)
    public Result removeTraining(String category, String id) {
        if (id == null) {
            return ok(managetraining.render(trainingRepository.getTrainingFrequencies(), userRepo.getAllTeachers(), trainingRepository.getTrainingByCategory(category), locationRepo.getAll(), null, "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null));
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
            return badRequest(managetraining.render(trainingRepository.getTrainingFrequencies(), userRepo.getAllTeachers(), trainingRepository.getTrainingByCategory(category), locationRepo.getAll(), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null));
        } else {
            Form<Training> filledForm = form.bindFromRequest();
            Training training = filledForm.get();
            training.set_id(trainingRepository.getTraining(code).get_id());
            trainingRepository.updateTraining(training);
            return ok(message.render("Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), "Training " + training.getName() + " is gewijzigd", "/managetraining"));
        }
    }

    private Map<String, String> mapValuesFromRequest(DynamicForm trainingData) {
        Map<String, String> baseValues = new HashMap<>();
        baseValues.put("TrainingCode", trainingData.get("TrainingCode"));
        baseValues.put("Name", trainingData.get("Name"));
        baseValues.put("Description", trainingData.get("Description"));
        baseValues.put("RequiredMaterial", trainingData.get("RequiredMaterial"));
        baseValues.put("Duration", trainingData.get("Duration"));
        baseValues.put("Tuition", trainingData.get("Tuition"));
        baseValues.put("Capacity", trainingData.get("Capacity"));
        baseValues.put("Category", trainingData.get("Category"));

        return baseValues;
    }

    private List<String> getValues(DynamicForm trainingData, String type) {
        List<String> dates = new ArrayList<>();
        List<String> locationIDs = new ArrayList<>();
        List<String> teacherIDs = new ArrayList<>();


        switch(type) {
            case "Date" :
                for(int i = 0; i < 50; i++) {
                String d = trainingData.field("Dates[" + i + "]").value();
                if(d == null) {
                    break;
                }
                dates.add(d);
                }
                return dates;
            case "Location" :
                for(int i = 0; i < 50; i++) {
                    String d = trainingData.field("LocationID[" + i + "]").value();
                    if(d == null) {
                        break;
                    }
                    locationIDs.add(d);
                }
                return locationIDs;
            case "Teacher" :
                for(int i = 0; i < 50; i++) {
                    String d = trainingData.field("TeacherID[" + i + "]").value();
                    if(d == null) {
                        break;
                    }
                    teacherIDs.add(d);
                }
                return teacherIDs;
        }
        return null;
    }

    private List<String> getDateIDs(List<String> dates, List<String> locationIDs, List<String> teacherIDs) throws ParseException {
        List<String> dateIDs = new ArrayList<>();

        int counter = 0;
        for(String d : dates) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = format.parse(d);
            DateTime dt = new DateTime(date, locationIDs.get(counter), teacherIDs.get(counter));
            String lastId = dateRepo.addDateTime(dt).toString();
            dateIDs.add(lastId);
            counter++;
        }

        return dateIDs;
    }
}
