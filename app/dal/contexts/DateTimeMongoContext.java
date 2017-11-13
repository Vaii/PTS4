package dal.contexts;

import com.mongodb.WriteResult;
import dal.DBConnector;
import dal.interfaces.DateTimeContext;
import models.DateTime;
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
    public boolean removeDateTime(DateTime dateTime) {
        WriteResult result = collection.remove(new ObjectId(dateTime.get_id()));
        return result.wasAcknowledged();
    }

    @Override
    public DateTime getDateTime(String date_id) {
        return collection.findOne("{_id:#}", new ObjectId(date_id)).as(DateTime.class);
    }

    public void removeAll() {
        collection.drop();
    }
}
