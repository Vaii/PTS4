package dal.repositories;

import dal.interfaces.LocationContext;
import models.Location;

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
    public boolean updateLocation(Location location) {
        return context.updateLocation(location);
    }

    @Override
    public boolean removeLocation(Location location) {
        return context.removeLocation(location);
    }

    @Override
    public List<Location> getAll() {
        return context.getAll();
    }

    @Override
    public Location getLocation(String city) {
        return context.getLocation(city);
    }
}
