package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dal.contexts.CategoryContext;
import dal.contexts.DateTimeMongoContext;
import dal.contexts.TrainingMongoContext;
import dal.contexts.UserMongoContext;
import dal.repositories.CategoryRepository;
import dal.repositories.DateTimeRepository;
import dal.repositories.TrainingRepository;
import dal.repositories.UserRepository;
import models.storage.Category;
import models.storage.User;
import models.util.DateConverter;
import models.view.ViewDate;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.example.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UtilityController extends Controller {

    private UserRepository userRepo = new UserRepository(new UserMongoContext("User"));
    private TrainingRepository trainingRepo = new TrainingRepository(new TrainingMongoContext("Training"));
    private DateTimeRepository dateRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));
    private CategoryRepository catRepo = new CategoryRepository(new CategoryContext("Category"));

    List<String> examples = new ArrayList<>();

    public Result checkEmail(String email) {
        if(userRepo.getUser(email) != null) {
            return ok("user_exists");
        } else {
            return ok("user_nonexists");
        }
    }

    public Result checkTraningCode(String trainingCode) {
        if(trainingRepo.getTraining(trainingCode) != null) {
            return ok("code_exists");
        } else {
            return ok("code_nonexists");
        }
    }

    public Result getTrainingForCategory(String category) {
        Category cat = catRepo.getCategoryByName(category);
        JsonNode node = Json.toJson(trainingRepo.getTrainingByCategory(cat.get_id()));

        return ok(node);
    }

    public Result getDatesForTraining(String trainingId) {
        User user = userRepo.getUser(ctx().session().get("email"));
        DateConverter converter = new DateConverter();
        List<ViewDate> dates;
        if(user != null) {
           dates = converter.getViewDatesWithoutTeacher(trainingId, user.getId(), true);
        } else {
            dates  = converter.getViewDatesWithoutTeacher(trainingId, "", true);
        }

        JsonNode node = Json.toJson(dates);
        return ok(node);
    }

    public Result addCategory(){
        Category newCategory;
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        String categoryStringValue = params.entrySet().iterator().next().getValue()[0];
        newCategory = new Category(categoryStringValue);
        catRepo.addCategory(newCategory);
        List<Category>categories = catRepo.getAllCategories();
        JsonNode node = Json.toJson(categories);
        return ok(node);
    }
  
    public Result checkUserSignUp(String dateId, String userId) {
        boolean result = dateRepo.checkUserSignup(userId, dateId);
        if (result) {
            return ok("user_signedup");
        } else {
            return ok("no_signup");
        }
    }

    // Example stuff below.
    public Result myPostAction() {
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        for (Map.Entry<String, String[]> param : params.entrySet()) {
            System.out.println(param.getKey() + " = " + param.getValue()[0]);
            examples.add(param.getValue()[0]);
            // Do other stuff here...
        }

        // Return the new complete list.
        // Of course you could fetch data from a database or any other source.
        JsonNode node = Json.toJson(examples);
        return ok(node);
    }

    public Result myExampleView() {
        return ok(example.render());
    }
}
