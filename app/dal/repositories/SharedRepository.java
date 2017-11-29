package dal.repositories;

import dal.interfaces.SharedContext;
import models.storage.Training;

import java.util.List;
import java.util.Map;

public class SharedRepository implements SharedContext {

    private SharedContext context;

    public SharedRepository(SharedContext context) {
        this.context = context;
    }

    @Override
    public List<Training> getTrainings(String userId) {
        return context.getTrainings(userId);
    }

    @Override
    public Boolean removeTraining(String trainingCode) {
        return context.removeTraining(trainingCode);
    }

    @Override
    public List<Training> getTrainingsForTeacher(String userId) {
        return context.getTrainingsForTeacher(userId);
    }

    @Override
    public Map<String, Integer> getTrainingFrequencies(String userId) {
        return context.getTrainingFrequencies(userId);
    }
}
