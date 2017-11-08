package dal.contexts;

import dal.DBConnector;
import dal.interfaces.SharedContext;
import dal.repositories.DateTimeRepository;
import dal.repositories.TrainingRepository;
import models.DateTime;
import models.Training;
import models.User;
import org.jongo.MongoCollection;

import java.util.ArrayList;
import java.util.List;

public class SharedMongoContext implements SharedContext {

    private TrainingRepository trainingRepo = new TrainingRepository(new TrainingMongoContext("Training"));
    private DateTimeRepository dateRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));

    public SharedMongoContext() {

    }

    @Override
    public List<Training> getTrainings(String userID) {
        List<Training> trainings= new ArrayList<>();
        List<Training> results = new ArrayList<>();
        trainings = trainingRepo.getAll();

        for(Training t : trainings) {
            for(String id : t.getDateIDs()) {
                DateTime date = dateRepo.getDateTime(id);
                for(String userId : date.getTrainees()) {
                    if(userId.equals(userID)) {
                        results.add(t);
                    }
                }
            }
        }

        return results;
    }
}
