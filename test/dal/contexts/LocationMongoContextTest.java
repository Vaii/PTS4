package dal.contexts;

import models.storage.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class LocationMongoContextTest {
    private LocationMongoContext context = new LocationMongoContext("LocationTest");
    private Location location1;
    private Location location2;
    private List<Location> locations;

    @Before
    public void setUp() {
        context.removeAll();
        location1 = new Location("Eindhoven", "Rachelsmolen", "1", "2.18",55 );
        location2 = new Location("Amsterdam", "Dam", "22A", "2.287B",150 );
        context.addLocation(location1);
        context.addLocation(location2);
        locations = context.getAll();
    }

    @Test
    public void addLocation() {
        reset();
        boolean result = context.addLocation(location1);
        assertEquals(true, result);
    }

    @Test
    public void updateLocation() {
        Location x = locations.get(0);
        x.setCapacity(12345);
        context.updateLocation(x.getId(), x);
        locations = context.getAll();
        assertEquals(12345, context.getLocation(x.getId()).getCapacity());
    }

    @Test
    public void removeLocation() {
        Location x = locations.get(0);
        context.removeLocation(x.getId());
        locations.clear();
        locations = context.getAll();
        assertEquals(1, locations.size());
    }

    @Test
    public void getAll() {
        reset();
        context.addLocation(location1);
        context.addLocation(location2);

        assertEquals(2, context.getAll().size());
    }

    @Test
    public void getLocation() {
        assertEquals(2, locations.size());
        Location location = context.getLocation(locations.get(0).getId());

        assertNotNull(location);
    }

    public void reset() {
        context.removeAll();
    }

}