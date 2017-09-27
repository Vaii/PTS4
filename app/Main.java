import dal.DBConnector;
import dal.contexts.TrainingMongoContext;
import dal.contexts.UserMongoContext;
import dal.repositories.TrainingRepository;
import models.Role;
import models.Training;
import models.User;
import org.jongo.MongoCollection;

import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        TrainingRepository trainingRepository = new TrainingRepository(new TrainingMongoContext("Training"));

        Training training = new Training("0001",
                "Test training",
                "This training is a test, it does not surve any other purpose then filling up space",
                "A laptop",
                5f,
                1250f,
                40,
                new Date(),
                null,
                null,
                new ArrayList<User>(),
                new ArrayList<Training>());

        trainingRepository.addTraining(training);
    }
}
