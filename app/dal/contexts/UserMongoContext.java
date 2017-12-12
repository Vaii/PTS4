package dal.contexts;

import com.mongodb.WriteResult;
import dal.DBConnector;
import dal.interfaces.UserContext;
import models.storage.Role;
import models.storage.User;
import org.apache.commons.codec.binary.Base64;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class UserMongoContext implements UserContext {
    private static final Logger LOGGER = Logger.getLogger(UserMongoContext.class.getName());
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

        if(user.getCompany().equalsIgnoreCase("infosupport")){
            WriteResult result = collection.insert("{firstName:#," +
                                                    " lastName:#," +
                                                    " email:#," +
                                                    " role:#," +
                                                    " company:#," +
                                                    " salt:#," +
                                                    " hashedPassword:#," +
                                                    " phoneNumber:#," +
                                                    " manager:#}",user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole(), user.getCompany(), salt, hashedPassword, user.getPhoneNumber(), user.getManager());
            return result.wasAcknowledged();
        }
        else{
            user.setRole(Role.EXTERN);
            WriteResult result = collection.insert("{firstName:#," +
                    " lastName:#," +
                    " email:#," +
                    " role:#," +
                    " company:#," +
                    " salt:#," +
                    " hashedPassword:#," +
                    " phoneNumber:#}",user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole(), user.getCompany(), salt, hashedPassword, user.getPhoneNumber());
            return result.wasAcknowledged();
        }
    }

    @Override
    public boolean removeUser(User user) {
        WriteResult result = collection.remove(new ObjectId(user.getId()));
        return result.wasAcknowledged();
    }

    @Override
    public User getUser(String firstName, String lastName) {
        return collection.findOne("{firstName:#, lastName:#}", firstName, lastName).as(User.class);
    }

    @Override
    public User getUser(String emailadress) {
        return collection.findOne("{email:#}", emailadress).as(User.class);
    }

    @Override
    public User getUserByID(String id) {
        return collection.findOne("{_id:#}", new ObjectId(id)).as(User.class);
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

        List<String> hashedDbPassword = collection.distinct("hashedPassword").query("{_id:#}", new ObjectId(user.getId())).as(String.class);
        List<String> dbSalt = collection.distinct("salt").query("{_id:#}", new ObjectId(user.getId())).as(String.class);

        WriteResult result = collection.update("{_id:#}", new ObjectId(user.getId())).with("{firstName:#," +
                " lastName:#," +
                " email:#," +
                " role:#," +
                " company:#," +
                " salt:#," +
                " hashedPassword:#," +
                " phoneNumber:#," +
                " manager:#," +
                        " skillIds:#}"
                        ,user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getRole(), user.getCompany(),dbSalt.get(0), hashedDbPassword.get(0), user.getPhoneNumber(), user.getManager(), user.getSkillIds());

        return result.wasAcknowledged();
    }

    @Override
    public boolean login(String email, String password) {
        List<String> hashedDbPassword = collection.distinct("hashedPassword").query("{email:#}", email).as(String.class);
        List<String> dbSalt = collection.distinct("salt").query("{email:#}", email).as(String.class);

        if(getUser(email) != null) {
            return checkLogin(password, hashedDbPassword.get(0), dbSalt.get(0));
        }

        return false;
    }

    @Override
    public List<User> getAllTeachers() {
        MongoCursor<User> results = collection.find("{role:#}", Role.DOCENT).as(User.class);
        List<User> teachers = new ArrayList<>();

        while (results.hasNext()) {
            User teacher = results.next();
            teachers.add(teacher);
        }
        return teachers;
    }

    @Override
    public List<User> getAllManagers() {

        MongoCursor<User> results = collection.find("{role:#}", Role.BUSINESSUNITMANAGER).as(User.class);
            List<User> managers = new ArrayList<>();

            while(results.hasNext()){
                managers.add(results.next());
            }

            return managers;
    }

    @Override
    public List<User> getUserByManager(String id) {

        MongoCursor<User> results = collection.find("{manager:#}", id).as(User.class);

        List<User> employees = new ArrayList<>();

        while(results.hasNext()){
            employees.add(results.next());
        }

        return employees;
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
            LOGGER.severe(e.getLocalizedMessage());
        }

        return generatedPassword;
    }

    /**
     * Generate a salt to be used when hashing passwords.
     *
     * @return The salt.
     */
    private String generateSalt() {
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        return Base64.encodeBase64String(salt);
    }

    /**
     * Be careful with this.
     */
    void removeAll() {
        collection.drop();
    }
}
