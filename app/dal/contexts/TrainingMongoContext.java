package dal.contexts;

import com.mongodb.WriteResult;
import dal.DBConnector;
import dal.interfaces.TrainingContext;
import models.storage.Location;
import models.storage.Training;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
        WriteResult result = collection.update("{_id:#}", new ObjectId(training.getId())).with(training);
        return result.wasAcknowledged();
    }

    @Override
    public boolean removeTraining(Training training) {
        WriteResult result = collection.remove(new ObjectId(training.getId()));
        return result.wasAcknowledged();
    }

    @Override
    public Training getTraining(String trainingCode) {
        return collection.findOne("{trainingCode:#}", trainingCode).as(Training.class);
    }

    @Override
    public List<Training> getTrainingsByDate(String dateId) {
        MongoCursor<Training> results = collection.find(" {dateIds: {$all: [#]}}", dateId).as(Training.class);
        List<Training> trainings = new ArrayList<>();

        while(results.hasNext()) {
            Training training = results.next();
            trainings.add(training);
        }
        return trainings;
    }

    @Override
    public List<Training> getTrainings(Location location) {
        MongoCursor<Training> results = collection.find("{location:#}", location).as(Training.class);
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

    @Override
    public List<Training> getTrainingByCategory(String category) {
        MongoCursor<Training> results = collection.find("{categoryid:#}", StringUtils.capitalize(category.toLowerCase())).as(Training.class);
        List<Training> trainings = new ArrayList<>();

        while(results.hasNext()) {
            Training training = results.next();
            trainings.add(training);
        }
        return trainings;
    }

    @Override
    public Map<String, Integer> getTrainingFrequencies() {
        Map<String, Integer> results = new TreeMap<>();
        List<Training> trainings;
        trainings = getAll();

        for(Training t : trainings) {
            if(!results.containsKey(t.getCategoryid())) {
                results.put(t.getCategoryid(), 1);
            } else {
                results.put(t.getCategoryid(), results.get(t.getCategoryid())+ 1);
            }
        }

        return results;
    }

    @Override
    public Training getTrainingById(String id) {
        return collection.findOne("{_id:#}", new ObjectId(id)).as(Training.class);
    }

    public void removeAll() {
        collection.drop();
    }
}
