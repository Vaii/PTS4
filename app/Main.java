import dal.contexts.UserMongoContext;
import dal.repositories.UserRepository;
import models.Role;
import models.User;

public class Main {

    public static void main(String[] args) {
        UserRepository repo = new UserRepository(new UserMongoContext("User"));

        repo.addUser(new User("Test", "Achternaam", "Test@test.nl", Role.AdviseurReward, "KimJungUnSoft Inc."), "wachtwoord");
    }
}
