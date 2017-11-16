package dal.interfaces;

import models.storage.Location;

import java.util.List;

public interface LocationContext {
    boolean addLocation(Location location);
    boolean updateLocation(String locationId, Location location);
    boolean removeLocation(String locationId);
    List<Location> getAll();
    Location getLocation(String id);
}
