package dal.contexts;

import com.mongodb.WriteResult;
import dal.DBConnector;
import dal.interfaces.DateTimeContext;
import models.DateTime;
import models.Training;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.Date;
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
        WriteResult result = collection.update("{_id:#}", new ObjectId(dateTime.get_id())).with(dateTime);
        return result.wasAcknowledged();
    }

    @Override
    public boolean removeDateTime(String dateTimeId) {
        WriteResult result = collection.remove(new ObjectId(dateTimeId));
        return result.wasAcknowledged();
    }

    @Override
    public DateTime getDateTime(String date_id) {
        return collection.findOne("{_id:#}", new ObjectId(date_id)).as(DateTime.class);
    }

    @Override
    public List<DateTime> getDateTimeForUser(String userId) {
        MongoCursor<DateTime> results = collection.find("{Trainees: { $all: [#]}}", userId).as(DateTime.class);
        List<DateTime> dateTimes = new ArrayList<>();

        while(results.hasNext()) {
            DateTime DateTime = results.next();
            dateTimes.add(DateTime);
        }
        return dateTimes;
    }

    @Override
    public List<DateTime> getDateTimeForTeacher(String teacherId) {
        MongoCursor<DateTime> results = collection.find("{teacherID:#}", teacherId).as(DateTime.class);
        List<DateTime> dateTimes = new ArrayList<>();

        while(results.hasNext()) {
            DateTime DateTime = results.next();
            dateTimes.add(DateTime);
        }
        return dateTimes;
    }

    public void removeAll() {
        collection.drop();
    }
}
