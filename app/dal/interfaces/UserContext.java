package dal.interfaces;

import models.storage.User;

import java.util.List;

public interface UserContext {
    boolean addUser(User user, String password);
    boolean removeUser(User user);
    User getUser(String firstName, String lastName);
    User getUser(String emailadress);
    User getUserByID(String id);
    long getTotalAmountUsers();
    List<User> getAll();
    List<User> getSpecificUsers(long offset, long recLimit);
    boolean updateUser(User user);
    boolean login(String email, String password);
    List<User>getAllTeachers();
    List<User>getSkilledTeachers(String catId);
    List<User>getAllManagers();
    List<User> getUserByManager(String id);
}
