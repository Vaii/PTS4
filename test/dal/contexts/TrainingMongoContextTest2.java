package dal.contexts;

import models.Location;
import models.Role;
import models.Training;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrainingMongoContextTest2 {
    private TrainingMongoContext context;

    // test location
    private Location location;

    // test users
    private User teacher;
    private User trainee;
    private User trainee2;
    private List<User> trainees;

    private Training preTraining;
    private Training training;
    private List<Training> prerequisites;

    @BeforeEach
    void setUp() {
        context = new TrainingMongoContext("TrainingTest");
        location = new Location("Eindhoven", "testStreet", "51", "R1_5.11", 55);

        teacher = new User("Marcel", "Koonen", "marcel@test.nl", Role.AdviseurReward, "Info Support");
        trainee = new User("Henk", "Vleuten", "henk@test.nl", Role.AdviseurReward, "Fictief bedrijf");
        trainee2 = new User("Jaap", "Van de Dam", "jaap@test.nl", Role.AdviseurReward, "Info Support");
        trainees = new ArrayList<>();
        trainees.add(trainee);
        trainees.add(trainee2);

        preTraining = new Training("004", "Java Basics", "Voor introductie met Java!",
                "Laptop", 2.00f, 800.00f, 20, new Date(2017, 9, 9), location, teacher,
                null, null);

        prerequisites = new ArrayList<>();
        prerequisites.add(preTraining);

        training = new Training("005", "Advanced Java", "Voor het verder ontwikkelen van uw Java skills!",
                "Laptop", 2.00f, 1000.00f, 25, new Date(2017, 10, 8), location, teacher,
                trainees, prerequisites);
    }

    @Test
    void addTraining() {
        reset();
        boolean result = context.addTraining(preTraining);
        assertEquals(true, result);
    }

    @Test
    void updateTraining() {
        reset();
        context.addTraining(preTraining);
        Training training = context.getTraining("004");
        assertEquals("Laptop", training.getRequiredMaterial());
        //assertEquals(800.00f, training.getTuition());

        training.setTuition(500.00f);
        context.updateTraining(training);
        Training training2 = context.getTraining("004");
      //  assertEquals(500.00f, training.getTuition());
    }

    @Test
    void removeTraining() {

    }

    @Test
    void getTraining() {
    }

    @Test
    void getTrainings() {
    }

    @Test
    void getTrainings1() {
    }

    @Test
    void getAll() {
    }

    @Test
    void removeAll() {
    }

    private void reset() {
        context.removeAll();
    }

}