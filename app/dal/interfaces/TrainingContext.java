package dal.interfaces;

import models.Location;
import models.Training;
import play.data.format.Formats;


import java.util.List;

public interface TrainingContext {
    boolean addTraining(Training training);
    boolean updateTraining(Training training);
    boolean removeTraining(Training training);
    Training getTraining(String trainingCode);
    List<Training> getTrainings(Formats.DateTime date);
    List<Training> getTrainings(Location location);
    List<Training> getAll();
}
