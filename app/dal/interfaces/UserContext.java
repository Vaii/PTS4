package dal.interfaces;

import models.storage.User;

import java.util.List;

public interface UserContext {
    boolean addUser(User user, String password);
    boolean removeUser(User user);
    User getUser(String firstName, String lastName);
    User getUser(String emailadress);
    User getUserByID(String id);
    List<User> getAll();
    boolean updateUser(User user);
    boolean login(String email, String password);
    List<User>getAllTeachers();
    List<User>getAllManagers();
    List<User> getUserByManager();
}
