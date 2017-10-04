package dal.interfaces;

import models.Location;
import play.mvc.Result;

import java.util.List;

public interface LocationContext {
    boolean addLocation(Location location);
    boolean updateLocation(Location location);
    boolean removeLocation(Location location);
    List<Location> getAll();
    Location getLocation(String id);
}
