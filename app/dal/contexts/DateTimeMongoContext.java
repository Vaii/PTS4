package dal.contexts;

import com.mongodb.WriteResult;
import dal.DBConnector;
import dal.interfaces.DateTimeContext;
import models.storage.DateTime;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.jongo.Update;

import java.util.ArrayList;
import java.util.List;

public class DateTimeMongoContext implements DateTimeContext {
    private DBConnector connector;
    private MongoCollection collection;

    public DateTimeMongoContext(String coll) {
        connector = new DBConnector();
        collection = connector.getCollection(coll);
    }

    @Override
    public Object addDateTime(DateTime dateTime) {
        WriteResult result = collection.save(dateTime);
        return result.getUpsertedId();
    }

    @Override
    public boolean updateDateTime(DateTime dateTime) {
        WriteResult result = collection.update("{_id:#}", new ObjectId(dateTime.getId())).with(dateTime);
        return result.wasAcknowledged();
    }

    @Override
    public boolean removeDateTime(String dateTimeId) {
        WriteResult result = collection.remove(new ObjectId(dateTimeId));
        return result.wasAcknowledged();
    }

    @Override
    public DateTime getDateTime(String dateId) {
        return collection.findOne("{_id:#}", new ObjectId(dateId)).as(DateTime.class);
    }

    @Override
    public List<DateTime> getDateTimeForUser(String userId) {
        MongoCursor<DateTime> results = collection.find("{trainees: { $all: [#]}}", userId).as(DateTime.class);
        List<DateTime> dateTimes = new ArrayList<>();

        while(results.hasNext()) {
            DateTime dateTime = results.next();
            dateTimes.add(dateTime);
        }
        return dateTimes;
    }

    @Override
    public List<DateTime> getDateTimeForTeacher(String teacherId) {
        MongoCursor<DateTime> results = collection.find("{teacherId:#}", teacherId).as(DateTime.class);
        List<DateTime> dateTimes = new ArrayList<>();

        while(results.hasNext()) {
            DateTime dateTime = results.next();
            dateTimes.add(dateTime);
        }
        return dateTimes;
    }

    @Override
    public boolean removeUser(String userId) {
        WriteResult result = collection.update("{}").multi().with("{$pull: {trainees: {$in: [#]}}}", userId);
        return result.wasAcknowledged();
    }

    @Override
    public boolean removeTeacher(String teacherId) {
        WriteResult result = collection.update("{teacherId:#}", teacherId).with("{ $set: { teacherId: #}}", "") ;
        return result.wasAcknowledged();
    }

    @Override
    public List<DateTime> getDateTimeForTraining(String trainingId) {
        MongoCursor<DateTime> results = collection.find("{trainingId:#}", trainingId).as(DateTime.class);
        List<DateTime> dateTimes = new ArrayList<>();

        while(results.hasNext()) {
            DateTime dateTime = results.next();
            dateTimes.add(dateTime);
        }
        return dateTimes;
    }

    @Override
    public List<DateTime> getDateTimeForLocation(String locationId) {
        MongoCursor<DateTime> results = collection.find("{locationId:#}", locationId).as(DateTime.class);
        List<DateTime> dateTimes = new ArrayList<>();

        while(results.hasNext()) {
            DateTime dateTime = results.next();
            dateTimes.add(dateTime);
        }
        return dateTimes;
    }

    @Override
    public boolean checkUserSignup(String userId, String dateId) {
        DateTime result = collection.findOne("{_id:#, trainees: { $all: [#]}}", new ObjectId(dateId), userId).as(DateTime.class);

        if(result != null) {
            return true;
        } else {
            return false;
        }
    }

    public void removeAll() {
        collection.drop();
    }
}
