package dal.contexts;

import dal.DBConnector;
import models.Role;
import models.User;
import org.jongo.MongoCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMongoContextTest {
    UserMongoContext context;
    private User testUser;
    private User testUser2;
    @BeforeEach
    void setUp() {
        context = new UserMongoContext("UserTest");
        testUser = new User("Henk", "TestPersoon", "Henk@test.nl", Role.External_Student, "Fictief bedrijf");
        testUser2 = new User("Jaap", "NormaalPersoon", "jaap@nieteentest.nl", Role.Business_Unit_Manager, "Info Support");
    }

    @Test
    void addUser() {
        reset();
        boolean result = context.addUser(testUser);
        assertEquals(true, result);
    }

    @Test
    void getUser() {
        reset();
        context.addUser(testUser);
        User user = context.getUser("Henk@test.nl");

        assertEquals("Henk", user.getFirstName());
    }

    @Test
    void getAll() {
        reset();
        context.addUser(testUser2);
        context.addUser(testUser);

        List<User> users = context.getAll();

        assertEquals(2, users.size());
    }

    @Test
    void updateUser() {
        reset();

        context.addUser(testUser);
        User user1 = context.getUser("Henk@test.nl");
        assertEquals("Henk", user1.getFirstName());

        testUser.setFirstName("Niet Henk");
        context.updateUser(testUser);
        User user2 = context.getUser("Henk@test.nl");

        assertEquals("Niet Henk", user2.getFirstName());
    }

    @Test
    void removeUser() {
        reset();
        context.addUser(testUser);
        assertEquals("Henk", context.getUser("Henk@test.nl").getFirstName());
        context.removeUser(testUser);
        assertEquals(null, context.getUser("Henk@test.nl"));
    }

    private void reset() {
        context.removeAll();
    }
}