package controllers;


import com.fasterxml.jackson.databind.JsonNode;
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
import views.html.shared.message;
import views.html.training.*;
import views.html.teacher.*;
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
    private TrainingRepository trainingRepo = new TrainingRepository(new TrainingMongoContext("Training"));
    private LocationRepository locationRepo = new LocationRepository(new LocationMongoContext("Location"));
    private UserRepository userRepo = new UserRepository(new UserMongoContext("User"));
    private DateTimeRepository dateRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));
    private SharedRepository sharedRepo = new SharedRepository(new SharedMongoContext());

    private FormFactory formFactory;
    private Form<Training> form;

    @Inject
    public TrainingController(FormFactory formFactory) {
        this.formFactory = formFactory;
        this.form = formFactory.form(Training.class);
        this.tuitionFormForm = formFactory.form(TuitionForm.class);
    }

    @Security.Authenticated(Secured.class)
    public Result signUpCourse(String id, String trainingID) {

        if (id == null) {
            return notFound();
        } else {
            if (Secured.getUserInfo(ctx()).getRole() != Role.Extern) {
                return ok(signUpCourseEmployee.render("Training inschrijven", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), dateRepo.getDateTime(id) , trainingRepo.getTraining(trainingID), tuitionFormForm));
            } else {
                DateTime signUpDate = dateRepo.getDateTime(id);
                signUpDate.addTrainee(Secured.getUserInfo(ctx()).get_id());
                dateRepo.updateDateTime(signUpDate);
                return ok(message.render("Succesvol Ingeschreven", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()),
                        "U bent succesvol ingeschreven voor de de training", "/"));
            }
        }
    }

    public Result signUpEmployee(String id, String trainingID) {

        Form<TuitionForm> filledTuitionForm = tuitionFormForm.bindFromRequest();

        if (filledTuitionForm.hasErrors()) {
            return badRequest(signUpCourseEmployee.render("Training inschrijven", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), dateRepo.getDateTime(id), trainingRepo.getTraining(trainingID), tuitionFormForm));
        } else {
            TuitionForm filledForm = filledTuitionForm.get();
            filledForm.setTotalCosts(filledForm.getStudyCosts() + filledForm.getAccommodationCosts() + filledForm.getExaminationFees() + filledForm.getTransportCosts() + filledForm.getExtraCosts());
            tutRepo.addForm(filledForm);

            DateTime signUpDate = dateRepo.getDateTime(id);
            signUpDate.addTrainee(Secured.getUserInfo(ctx()).get_id());
            dateRepo.updateDateTime(signUpDate);
            return ok(message.render("Inschrijving ingediend", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()),
                    "De aanvraag voor de training is succesvol ingedient", "/"));
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
            Form<Training> form2 = form.bind(baseValues);
            if(trainingRepo.getTraining(training.getTrainingCode()) != null) {
                return badRequest(addtraining.render(form2.withError("TrainingCode", "Trainingscode moet uniek"), Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), "Add Training", locationRepo.getAll(), userRepo.getAllTeachers(), null, null));
            }

            List<String> dateIDs = getDateIDs(dates, locationIDs, teacherIDs);

            training.setDateIDs(dateIDs);

            trainingRepo.addTraining(training);
            Training t = trainingRepo.getTraining(training.getTrainingCode());
            return ok(message.render("Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), "Training " + t.getName() + " is aangemaakt", "/managetraining"));
        }
    }

    public Result overview() {
        return ok(trainingoverview.render(trainingRepo.getTrainingFrequencies() ,new ArrayList<>(), null,
                "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), null));
    }

    public Result overviewCategory(String category) {
        if(category == null) {
            return ok(trainingoverview.render(trainingRepo.getTrainingFrequencies(),new ArrayList<>(), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), null));
        } else {
            return ok(trainingoverview.render(trainingRepo.getTrainingFrequencies(), trainingRepo.getTrainingByCategory(category), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), null));
        }

    }

    public Result trainingOverview(String category, String id) {
        if (id == null) {
            return ok(trainingoverview.render(trainingRepo.getTrainingFrequencies(), trainingRepo.getTrainingByCategory(category), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), null));
        } else {
            Training t = trainingRepo.getTraining(id);

            List<ViewDate> viewDates = new ArrayList<>();

            int counter = 0;
            for(String dateTime : t.getDateIDs()) {
                DateTime d = dateRepo.getDateTime(dateTime);

                Location loc = locationRepo.getLocation(d.getLocationID());
                User teacher = userRepo.getUserByID(d.getTeacherID());
                ViewDate vd = new ViewDate(t.getDateIDs().get(counter), d.getDate(), loc, teacher);
                viewDates.add(vd);
                counter++;
            }

            return ok(trainingoverview.render(trainingRepo.getTrainingFrequencies(), trainingRepo.getTrainingByCategory(category), trainingRepo.getTraining(id),
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), viewDates));
        }
    }

    @Security.Authenticated(Secured.class)
    public Result manage() {
        return ok(managetraining.render(trainingRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), new ArrayList<>(), locationRepo.getAll(), null, "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null));
    }

    public Result manageCategory(String category) {
        if (category == null) {
            return ok(managetraining.render(trainingRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), new ArrayList<>(), locationRepo.getAll(), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null));
        } else {
            return ok(managetraining.render(trainingRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), trainingRepo.getTrainingByCategory(category), locationRepo.getAll(), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null));
        }
    }

    @Security.Authenticated(Secured.class)
    public Result manageTraining(String category, String id) {
        if (id == null) {
            return ok(managetraining.render(trainingRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), trainingRepo.getTrainingByCategory(category), locationRepo.getAll(), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null));
        } else {
            Training t = trainingRepo.getTraining(id);

            List<ViewDate> viewDates = new ArrayList<>();

            int counter = 0;
            for(String dateTime : t.getDateIDs()) {
                DateTime d = dateRepo.getDateTime(dateTime);

                Location loc = locationRepo.getLocation(d.getLocationID());
                User teacher = userRepo.getUserByID(d.getTeacherID());
                ViewDate vd = new ViewDate(t.getDateIDs().get(counter), d.getDate(), loc, teacher);
                viewDates.add(vd);
                counter++;
            }

            Form<Training> editForm = form.fill(trainingRepo.getTraining(id));
            return ok(managetraining.render(trainingRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), trainingRepo.getTrainingByCategory(category), locationRepo.getAll(), trainingRepo.getTraining(id),
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), editForm, viewDates));
        }
    }

    @Security.Authenticated(Secured.class)
    public Result removeTraining(String category, String id) {
        if (id == null) {
            return ok(managetraining.render(trainingRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), trainingRepo.getTrainingByCategory(category), locationRepo.getAll(), null, "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null));
        } else {
            Training t = trainingRepo.getTraining(id);
            sharedRepo.removeTraining(t.getTrainingCode());
            return ok(removetraining.render(t, "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
        }

    }

    @Security.Authenticated(Secured.class)
    public Result edit(String category, String code) throws ParseException {
        //Form<Training> editFrom = form.fill(trainingRepo.getTraining(code));

        DynamicForm trainingData = formFactory.form().bindFromRequest();

        Map<String, String> baseValues = mapValuesFromRequest(trainingData);

        List<String> dates = getValues(trainingData, "Date");
        List<String> locationIDs = getValues(trainingData, "Location");
        List<String> teacherIDs = getValues(trainingData, "Teacher");

        if (trainingData.hasErrors()) {
            flash("danger", "Wrong values");
            return badRequest(managetraining.render(trainingRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), trainingRepo.getTrainingByCategory(category), locationRepo.getAll(), null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null));
        } else {
            Training training = form.bind(baseValues).get();

            List<String> dateIDs = getDateIDs(dates, locationIDs, teacherIDs);

            training.setDateIDs(dateIDs);
            training.set_id(trainingRepo.getTraining(code).get_id());

            trainingRepo.updateTraining(training);
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
        baseValues.put("Category", trainingData.get("category"));

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

    @Security.Authenticated(Secured.class)
    public Result teacherTrainingOverview() {
        return ok(teachertrainingoverview.render(sharedRepo.getTrainings(Secured.getUserInfo(ctx()).get_id()),"Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), null));
    }

}

