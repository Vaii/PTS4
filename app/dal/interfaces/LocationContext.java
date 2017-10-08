package dal.interfaces;

import models.Location;
import play.mvc.Result;

import java.util.List;

public interface LocationContext {
    boolean addLocation(Location location);
    boolean updateLocation(String location_id, Location location);
    boolean removeLocation(String location_id);
    List<Location> getAll();
    Location getLocation(String id);
}
