package dal.repositories;

import dal.interfaces.TrainingContext;
import models.storage.Location;
import models.storage.Training;
import play.data.format.Formats;

import java.util.List;
import java.util.Map;

public class TrainingRepository implements TrainingContext {
    private TrainingContext context;

    public TrainingRepository(TrainingContext context) {
        this.context = context;
    }

    @Override
    public boolean addTraining(Training training) {
        return context.addTraining(training);
    }

    @Override
    public boolean updateTraining(Training training) {
        return context.updateTraining(training);
    }

    /**
     * Removes training without removing datetime objects.
     *
     * @deprecated use {@link SharedRepository.removeTraining()} instead.
     */
    @Deprecated
    @Override
    public boolean removeTraining(Training training) {
        return context.removeTraining(training);
    }

    @Override
    public Training getTraining(String trainingCode) {
        return context.getTraining(trainingCode);
    }

    @Override
    public List<Training> getTrainings(Formats.DateTime date) {
        return context.getTrainings(date);
    }

    @Override
    public List<Training> getTrainings(Location location) {
        return context.getTrainings(location);
    }

    @Override
    public List<Training> getAll() {
        return context.getAll();
    }

    @Override
    public List<Training> getTrainingByCategory(String category) {
        return context.getTrainingByCategory(category);
    }

    @Override
    public Map<String, Integer> getTrainingFrequencies() {
        return context.getTrainingFrequencies();
    }

    @Override
    public Training getTrainingById(String id) {
        return context.getTrainingById(id);
    }
}
