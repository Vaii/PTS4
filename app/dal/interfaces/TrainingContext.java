package dal.interfaces;

import models.Location;
import models.Training;
import models.User;
import play.data.format.Formats;


import java.util.List;
import java.util.Map;

public interface TrainingContext {
    boolean addTraining(Training training);
    boolean updateTraining(Training training);
    boolean removeTraining(Training training);
    Training getTraining(String trainingCode);
    List<Training> getTrainings(Formats.DateTime date);
    List<Training> getTrainings(Location location);
    List<Training> getAll();
    List<Training> getTrainingByCategory(String category);
    Map<String, Integer> getTrainingFrequencies();
}
