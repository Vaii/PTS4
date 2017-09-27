package dal.repositories;

import dal.interfaces.UserContext;
import models.User;

import java.util.List;

public class UserRepository implements UserContext {

    private UserContext context;

    public UserRepository(UserContext context) {
        this.context = context;
    }

    @Override
    public boolean addUser(User user) {
        return context.addUser(user);
    }

    @Override
    public boolean removeUser(User user) {
        return context.removeUser(user);
    }

    @Override
    public User getUser(String firstName, String lastName) {
        return context.getUser(firstName, lastName);
    }

    @Override
    public User getUser(String emailadress) {
        return context.getUser(emailadress);
    }

    @Override
    public List<User> getAll() {
        return context.getAll();
    }

    @Override
    public boolean updateUser(User user) {
        return context.updateUser(user);
    }
}
