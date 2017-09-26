package dal.contexts;

import com.mongodb.WriteResult;
import dal.DBConnector;
import dal.interfaces.TrainingContext;
import models.Location;
import models.Training;
import models.User;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import play.data.format.Formats;

import java.util.ArrayList;
import java.util.List;

public class TrainingMongoContext implements TrainingContext {
    private DBConnector connector;
    private MongoCollection collection;

    public TrainingMongoContext(String coll) {
        connector = new DBConnector();
        collection = connector.getCollection(coll);
    }

    @Override
    public boolean addTraining(Training training) {
        WriteResult result = collection.save(training);
        return result.wasAcknowledged();
    }

    @Override
    public boolean updateTraining(Training training) {
        WriteResult result = collection.save(training);
        return result.wasAcknowledged();
    }

    @Override
    public boolean removeTraining(Training training) {
        WriteResult result = collection.remove(new ObjectId(training.get_id()));
        return result.wasAcknowledged();
    }

    @Override
    public Training getTraining(String trainingCode) {
        return collection.findOne("{TrainingsCode:#}", trainingCode).as(Training.class);
    }

    @Override
    public List<Training> getTrainings(Formats.DateTime date) {
        MongoCursor<Training> results = collection.find("{Date:#}", date).as(Training.class);
        List<Training> trainings = new ArrayList<>();

        while(results.hasNext()) {
            Training training = results.next();
            trainings.add(training);
        }
        return trainings;    }

    @Override
    public List<Training> getTrainings(Location location) {
        MongoCursor<Training> results = collection.find("{Location:#}", location).as(Training.class);
        List<Training> trainings = new ArrayList<>();

        while(results.hasNext()) {
            Training training = results.next();
            trainings.add(training);
        }
        return trainings;
    }

    @Override
    public List<Training> getAll() {
        MongoCursor<Training> results = collection.find().as(Training.class);
        List<Training> trainings = new ArrayList<>();

        while(results.hasNext()) {
            Training training = results.next();
            trainings.add(training);
        }
        return trainings;
    }
}
