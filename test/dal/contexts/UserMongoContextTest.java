package dal.contexts;

import models.Role;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMongoContextTest {
    UserMongoContext context;
    private User testUser;
    private User testUser2;
    @BeforeEach
    void setUp() {
        context = new UserMongoContext("UserTest");
        testUser = new User("Henk", "TestPersoon", "Henk@test.nl", Role.AdviseurReward, "Fictief bedrijf");
        testUser2 = new User("Jaap", "NormaalPersoon", "jaap@nieteentest.nl", Role.ManagerHRD, "Info Support");
    }

    @Test
    void addUser() {
        reset();
        boolean result = context.addUser(testUser, "123");
        assertEquals(true, result);
    }

    @Test
    void getUser() {
        reset();
        context.addUser(testUser, "123");
        User user = context.getUser("Henk@testone.nl");

        assertEquals("Henk", user.getFirstName());
    }

    @Test
    void getAll() {
        reset();
        context.addUser(testUser2, "123");
        context.addUser(testUser, "123");

        List<User> users = context.getAll();

        assertEquals(2, users.size());
    }

    @Test
    void updateUser() {
        reset();

        context.addUser(testUser, "123");
        User user1 = context.getUser("Henk@testone.nl");
        assertEquals("Henk", user1.getFirstName());

        testUser.setFirstName("Niet Henk");
        context.updateUser(testUser);
        User user2 = context.getUser("Henk@testone.nl");

        assertEquals("Niet Henk", user2.getFirstName());
    }

    @Test
    void removeUser() {
        reset();
        context.addUser(testUser, "123");
        assertEquals("Henk", context.getUser("Henk@testone.nl").getFirstName());
        context.removeUser(testUser);
        assertEquals(null, context.getUser("Henk@testone.nl"));
    }

    @Test
    void login() {
        reset();
        context.addUser(testUser, "123");
        boolean resultFalse = context.login("Henk@testone.nl", "1234");
        assertEquals(false, resultFalse);

        boolean resultTrue = context.login("Henk@testone.nl", "123");
        assertEquals(true, resultTrue);
    }

    private void reset() {
        context.removeAll();
    }
}