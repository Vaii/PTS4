package dal.contexts;

import com.mongodb.WriteResult;
import dal.DBConnector;
import dal.interfaces.LocationContext;
import models.storage.Location;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

public class LocationMongoContext implements LocationContext{
    private DBConnector connector;
    private MongoCollection collection;

    public LocationMongoContext(String coll) {
        connector = new DBConnector();
        collection = connector.getCollection(coll);
    }

    @Override
    public boolean addLocation(Location location) {
        WriteResult result = collection.save(location);
        return result.wasAcknowledged();
    }

    @Override
    public boolean updateLocation(String locationId, Location location) {
        WriteResult result = collection.update("{_id:#}", new ObjectId(locationId)).with(location);
        return result.wasAcknowledged();
    }

    @Override
    public boolean removeLocation(String locationId) {
        WriteResult result = collection.remove(new ObjectId(locationId));
        return result.wasAcknowledged();
    }

    @Override
    public List<Location> getAll() {
        MongoCursor<Location> results = collection.find().as(Location.class);
        List<Location> locations = new ArrayList<>();

        while(results.hasNext()) {
            Location location = results.next();
            locations.add(location);
        }
        return locations;
    }

    @Override
    public Location getLocation(String id) {
        return collection.findOne("{_id:#}", new ObjectId(id)).as(Location.class);
    }

    public void removeAll() {
        collection.drop();
    }
}
