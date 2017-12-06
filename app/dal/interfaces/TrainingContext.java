package dal.interfaces;

import models.storage.Location;
import models.storage.Training;
import play.data.format.Formats;


import java.util.List;
import java.util.Map;

public interface TrainingContext {
    boolean addTraining(Training training);
    boolean updateTraining(Training training);
    boolean removeTraining(Training training);
    Training getTraining(String trainingCode);
    List<Training> getTrainingsByDate(String dateId);
    List<Training> getTrainings(Location location);
    List<Training> getAll();
    List<Training> getTrainingByCategory(String category);
    Map<String, Integer> getTrainingFrequencies();
    Training getTrainingById(String id);
}
