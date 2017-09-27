package dal.repositories;

import dal.interfaces.TrainingContext;
import models.Location;
import models.Training;
import play.data.format.Formats;

import java.util.List;

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
}
