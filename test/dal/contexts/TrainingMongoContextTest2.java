package dal.contexts;

import models.Location;
import models.Role;
import models.Training;
import models.User;
import org.junit.Before;
import org.junit.Test;


import java.util.*;

import static org.junit.Assert.assertEquals;


class TrainingMongoContextTest2 {
    private TrainingMongoContext context;

    // test location
    private Location location;

    // test users
    private User teacher;
    private User trainee;
    private User trainee2;
    private List<String> trainees;

    private Training preTraining;
    private Training training;
    private List<String> prerequisites;

    @Before
    void setUp() {
        context = new TrainingMongoContext("TrainingTest");
        location = new Location("Eindhoven", "testStreet", "51", "R1_5.11", 55);

        teacher = new User("Marcel", "Koonen", "marcel@test.nl", Role.AdviseurReward, "Info Support", "0612345678");
        trainee = new User("Henk", "Vleuten", "henk@test.nl", Role.AdviseurReward, "Fictief bedrijf", "0612345678");
        trainee2 = new User("Jaap", "Van de Dam", "jaap@test.nl", Role.AdviseurReward, "Info Support", "0612345678");
        trainees = new ArrayList<>();
        trainees.add(trainee.get_id());
        trainees.add(trainee2.get_id());

        preTraining = new Training("004", "Java Basics", "Voor introductie met Java!",
                "Laptop", 2.00f, 800.00f, 20, new Date(2017, 9, 9), "java", location.get_id(), teacher.get_id(),
                null, null);

        prerequisites = new ArrayList<>();
        prerequisites.add(preTraining.get_id());

        training = new Training("005", "Advanced Java", "Voor het verder ontwikkelen van uw Java skills!",
                "Laptop", 2.00f, 1000.00f, 25, new Date(2017, 10, 8), "JAVA", location.get_id(), teacher.get_id(),
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
        Training training1 = context.getTraining("004");
        assertEquals("Laptop", training1.getRequiredMaterial());
        assertEquals(800.00f, (float)training1.getTuition());

        training1.setTuition(500.00f);
        context.updateTraining(training1);
        Training training2 = context.getTraining("004");
        assertEquals(500.00f, (float)training2.getTuition());
    }

    @Test
    void removeTraining() {
        reset();
        context.addTraining(training);
        assertEquals("Advanced Java", context.getTraining("005").getName());
        context.removeTraining(training);
        assertEquals(null, context.getTraining("005"));
    }

    @Test
    void getTraining() {
        reset();
        context.addTraining(training);
        assertEquals("Advanced Java", context.getTraining("005").getName());
    }

    @Test
    void getAll() {
        reset();
        context.addTraining(preTraining);
        context.addTraining(training);

        assertEquals(2, context.getAll().size());
    }

    @Test
    void getTrainingByCategory() {
        reset();
        context.addTraining(preTraining);
        context.addTraining(training);

        List<Training> results = new ArrayList<>();
        results = context.getTrainingByCategory("java");
        assertEquals(2, results.size());

        results.clear();

        assertEquals(0, results.size());

        results = context.getTrainingByCategory("JAVA");
        assertEquals(2, results.size());
    }

    @Test
    void getTrainingFrequency() {
        reset();
        context.addTraining(preTraining);
        context.addTraining(training);

        Map<String, Integer> results = new TreeMap<>();
        results = context.getTrainingFrequencies();

        assertEquals(1, results.size());

        assertEquals(2,(int) results.get("Java"));

    }

    private void reset() {
        context.removeAll();
    }

}