package dal.contexts;

import com.mongodb.WriteResult;
import dal.DBConnector;
import dal.interfaces.DateTimeContext;
import models.storage.DateTime;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

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

    public void removeAll() {
        collection.drop();
    }
}
