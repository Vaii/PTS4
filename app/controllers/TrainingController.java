package controllers;

import models.Training;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.trainingoverview;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ken on 27-9-2017.
 */
public class TrainingController extends Controller{

    //TrainingRepository trainingRepository = new TrainingRepository(context);

  //  public final List<Training> trainingList;

    @Inject
    public TrainingController(){
    /*    trainingList = new ArrayList<Training>();
        trainingList.add(new Training("TestTraining"));
        trainingList.add(new Training("TestTraining"));
        trainingList.add(new Training("TestTraining"));
        trainingList.add(new Training("TestTraining"));
        trainingList.add(new Training("TestTraining"));
*/
    }
    public Result overview() {
        return ok(trainingoverview.render());
    }


    //List<Training> training = new ArrayList<Training>();




}
