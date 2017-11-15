package dal.contexts;

import models.storage.Training;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TrainingMongoContextTest2 {
    //contexts
    private TrainingMongoContext TrainingContext;
    private Training training;


    @Before
   public void setUp() {
        TrainingContext = new TrainingMongoContext("TrainingTest");
        TrainingContext.removeAll();
        training = new Training("004", "Java", "Beginnen met Java", "Laptop", 2.0f, 20.0f, 20, null, "Java", null);
        TrainingContext.addTraining(training);
    }


    @Test
    public void addTraining() {
        reset();
        boolean result = TrainingContext.addTraining(training);
        assertEquals(true, result);
    }

    @Test
    public void updateTraining() {
        reset();
        TrainingContext.addTraining(training);
        Training training1 = TrainingContext.getTraining("004");
        assertEquals("Laptop", training1.getRequiredMaterial());
        assertEquals(20.0f, training1.getTuition(), 0.01f);

        training1.setTuition(500.00f);
        TrainingContext.updateTraining(training1);
        Training training2 = TrainingContext.getTraining("004");
        assertEquals(500.00f, (float)training2.getTuition(),0.01f);
    }

    @Test
    public void removeTraining() {
        reset();
        TrainingContext.addTraining(training);
        assertEquals("Java", TrainingContext.getTraining("004").getName());
        TrainingContext.removeTraining(training);
        assertEquals(null, TrainingContext.getTraining("004"));
    }

    @Test
    public void getTraining() {
        reset();
        TrainingContext.addTraining(training);
        assertEquals("Java", TrainingContext.getTraining("004").getName());
    }

    @Test
    public void getAll() {
        reset();
        TrainingContext.addTraining(training);
        assertEquals(1, TrainingContext.getAll().size());
    }

    @Test
    public void getTrainingByCategory() {
        reset();
        TrainingContext.addTraining(training);

        List<Training> results = new ArrayList<>();
        results = TrainingContext.getTrainingByCategory("Java");
        assertEquals(1, results.size());

        results.clear();

        assertEquals(0, results.size());

        results = TrainingContext.getTrainingByCategory("Java");
        assertEquals(1, results.size());
    }

    @Test
    public void getTrainingFrequency() {
        reset();
        TrainingContext.addTraining(training);

        Map<String, Integer> results = new TreeMap<>();
        results = TrainingContext.getTrainingFrequencies();

        assertEquals(1, results.size());

        assertEquals(1,(int) results.get("Java"));

    }

    private void reset() {
        TrainingContext.removeAll();
    }

}