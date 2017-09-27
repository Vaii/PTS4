package dal.interfaces;

import models.User;

import java.util.List;

public interface UserContext {
    boolean addUser(User user, String password);
    boolean removeUser(User user);
    User getUser(String firstName, String lastName);
    User getUser(String emailadress);
    List<User> getAll();
    boolean updateUser(User user);
    boolean login(String email, String password);
}
