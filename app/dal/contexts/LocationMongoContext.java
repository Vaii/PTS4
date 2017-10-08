package dal.contexts;

import com.mongodb.WriteResult;
import dal.DBConnector;
import dal.interfaces.LocationContext;
import models.Location;
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
    public boolean updateLocation(String location_id, Location location) {
        collection.update(new ObjectId(location_id), location);
        return true;
    }

    @Override
    public boolean removeLocation(Location location) {
        WriteResult result = collection.remove(new ObjectId(location.get_id()));
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
