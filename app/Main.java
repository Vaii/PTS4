import DAL.DBConnector;
import models.Role;
import models.User;
import org.jongo.MongoCollection;

public class Main {

    public static void main(String[] args) {
        DBConnector con = new DBConnector();

        MongoCollection tests = con.getCollection("test");

        User user = new User("Henk", "testPersoon", "henk@test.nl", Role.Business_Unit_Manager, "Fontys");

        tests.save(user);
    }
}
