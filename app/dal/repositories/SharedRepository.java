package dal.repositories;

import dal.interfaces.SharedContext;
import models.Training;

import java.util.List;

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
}
