package dal.contexts;

import com.mongodb.WriteResult;
import dal.DBConnector;
import dal.interfaces.UserContext;
import models.User;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

public class UserMongoContext implements UserContext {
    private DBConnector connector;
    private MongoCollection collection;

    public UserMongoContext(String coll) {
        connector = new DBConnector();
        collection = connector.getCollection(coll);
    }

    @Override
    public boolean addUser(User user, String password) {
        String salt = user.generateSalt();
        String hashedPassword = user.generatePassword(password, salt);

        user.setSalt(salt);
        user.setHashedPassword(hashedPassword);

        WriteResult result = collection.save(user);
        return result.wasAcknowledged();
    }

    @Override
    public boolean removeUser(User user) {
        WriteResult result = collection.remove(new ObjectId(user.get_id()));
        return result.wasAcknowledged();
    }

    @Override
    public User getUser(String firstName, String lastName) {
        return collection.findOne("{FirstName:#, LastName:#}", firstName, lastName).as(User.class);
    }

    @Override
    public User getUser(String emailadress) {
        return collection.findOne("{Email:#}", emailadress).as(User.class);
    }

    @Override
    public List<User> getAll() {
        MongoCursor<User> results = collection.find().as(User.class);
        List<User> users = new ArrayList<>();

        while(results.hasNext()) {
            User user = results.next();
            users.add(user);
        }
        return users;
    }

    @Override
    public boolean updateUser(User user) {
        WriteResult result = collection.save(user);
        return result.wasAcknowledged();
    }

    @Override
    public boolean login(String email, String password) {
        User userToCheck = getUser(email);

        if(userToCheck != null) {
            return userToCheck.checkLogin(password);
        }

        return false;
    }

    /**
     * Be careful with this.
     */
    public void removeAll() {
        collection.drop();
    }
}
