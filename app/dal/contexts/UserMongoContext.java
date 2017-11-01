package dal.contexts;

import com.mongodb.WriteResult;
import dal.DBConnector;
import dal.interfaces.UserContext;
import models.Role;
import models.User;
import org.apache.commons.codec.binary.Base64;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.jongo.Update;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserMongoContext implements UserContext {
    private DBConnector connector;
    private MongoCollection collection;

    public UserMongoContext(String coll) {
        connector = new DBConnector();
        collection = connector.getCollection(coll);
    }

    @Override
    public boolean addUser(User user, String password) {
        String salt = generateSalt();
        String hashedPassword = generatePassword(password, salt);

        if(user.getCompany().toLowerCase().equals("infosupport")){
            WriteResult result = collection.insert("{FirstName:#," +
                                                    " LastName:#," +
                                                    " Email:#," +
                                                    " Role:#," +
                                                    " Company:#," +
                                                    " Salt:#," +
                                                    " HashedPassword:#," +
                                                    " PhoneNumber:#}",user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole(), user.getCompany(), salt, hashedPassword, user.getPhoneNumber());
            return result.wasAcknowledged();
        }
        else{
            user.setRole(Role.Extern);
            WriteResult result = collection.insert("{FirstName:#," +
                    " LastName:#," +
                    " Email:#," +
                    " Role:#," +
                    " Company:#," +
                    " Salt:#," +
                    " HashedPassword:#," +
                    " PhoneNumber:#}",user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole(), user.getCompany(), salt, hashedPassword, user.getPhoneNumber());
            return result.wasAcknowledged();
        }
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

        while (results.hasNext()) {
            User user = results.next();
            users.add(user);
        }
        return users;
    }

    @Override
    public boolean updateUser(User user) {
        WriteResult result = collection.update("{_id:#}", user.get_id()).with("{FirstName:#," +
                " LastName:#," +
                " Email:#," +
                " Role:#," +
                " Company:#," +
                " PhoneNumber:#}",user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole(), user.getCompany(), user.getPhoneNumber());

        return result.wasAcknowledged();
    }

    @Override
    public boolean login(String email, String password) {
        List<String> HashedDBPassword = collection.distinct("HashedPassword").query("{Email:#}", email).as(String.class);
        List<String> DbSalt = collection.distinct("Salt").query("{Email:#}", email).as(String.class);

        if(getUser(email) != null) {
            return checkLogin(password, HashedDBPassword.get(0), DbSalt.get(0));
        }

        return false;
    }

    @Override
    public List<User> getAllTeachers() {
        MongoCursor<User> results = collection.find("{Role:#}", Role.Docent).as(User.class);
        List<User> teachers = new ArrayList<>();

        while (results.hasNext()) {
            User teacher = results.next();
            teachers.add(teacher);
        }
        return teachers;
    }

    /**
     * Checks the login of a user by comparing the given plain text password with the salt and hashed password in the database.
     *
     * @param password The password to verify in plain text.
     * @return True if the login is successful.
     */
    public boolean checkLogin(String password, String dbPassword, String salt) {
        String passwordToCheck = generatePassword(password, salt);
        return passwordToCheck.equals(dbPassword);
    }

    // All password related stuff is down below.

    /**
     * Hashes a password and salt combination using SHA-512.
     *
     * @param passwordToHash The password in plain text.
     * @param salt           THe salt to add to the password.
     * @return The hashed combination of password and salt.
     */
    public String generatePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedPassword;
    }

    /**
     * Generate a salt to be used when hashing passwords.
     *
     * @return The salt.
     */
    public String generateSalt() {
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        String encodedSalt = Base64.encodeBase64String(salt);
        return encodedSalt;
    }

    /**
     * Be careful with this.
     */
    public void removeAll() {
        collection.drop();
    }
}
