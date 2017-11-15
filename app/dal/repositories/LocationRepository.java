package dal.repositories;

import dal.interfaces.LocationContext;
import models.storage.Location;

import java.util.List;

public class LocationRepository implements LocationContext{
    private LocationContext context;

    public LocationRepository(LocationContext context) {
        this.context = context;
    }

    @Override
    public boolean addLocation(Location location) {
        return context.addLocation(location);
    }

    @Override
    public boolean updateLocation(String location_id, Location location) {
        return context.updateLocation(location_id, location);
    }

    @Override
    public boolean removeLocation(String location_id) {
        return context.removeLocation(location_id);
    }

    @Override
    public List<Location> getAll() {
        return context.getAll();
    }

    @Override
    public Location getLocation(String id) {
        return context.getLocation(id);
    }
}
