package dal.contexts;

import models.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationMongoContextTest {
    private LocationMongoContext context;
    private Location location1;
    private Location location2;

    @BeforeEach
    void setUp() {
        context = new LocationMongoContext("LocationTest");
        location1 = new Location("Eindhoven", "51", "R1_5.11", 55);
        location2 = new Location("Baexem", "33", "8.7", 20);
    }

    @Test
    void addLocation() {
        reset();
        boolean result = context.addLocation(location1);
        assertEquals(true, result);
    }

    @Test
    void updateLocation() {
        reset();
        context.addLocation(location1);
        Location location = context.getLocation("Eindhoven");
        assertEquals("51", location.getStreetNumber());

        location1.setStreetNumber("15");
        context.updateLocation(location1);
        Location location3 = context.getLocation("Eindhoven");
        assertEquals("15", location3.getStreetNumber());

    }

    @Test
    void removeLocation() {
        reset();
        context.addLocation(location1);
        assertEquals("51", context.getLocation("Eindhoven").getStreetNumber());
        context.removeLocation(location1);
        assertEquals(null, context.getLocation("Eindhoven"));
    }

    @Test
    void getAll() {
        reset();
        context.addLocation(location1);
        context.addLocation(location2);

        assertEquals(2, context.getAll().size());
    }

    @Test
    void getLocation() {
        reset();
        context.addLocation(location1);
        assertEquals("51", context.getLocation("Eindhoven").getStreetNumber());
    }

    private void reset() {
        context.removeAll();
    }

}