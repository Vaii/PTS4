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
    private TrainingMongoContext trainingContext;
    private Training training;


    @Before
   public void setUp() {
        trainingContext = new TrainingMongoContext("TrainingTest");
        trainingContext.removeAll();
        training = new Training("004", "Java", "Beginnen met Java", "Laptop", 2.0f, 20.0f, 20, null, "Java", null);
        trainingContext.addTraining(training);
    }


    @Test
    public void addTraining() {
        reset();
        boolean result = trainingContext.addTraining(training);
        assertEquals(true, result);
    }

    @Test
    public void updateTraining() {
        reset();
        trainingContext.addTraining(training);
        Training training1 = trainingContext.getTraining("004");
        assertEquals("Laptop", training1.getRequiredMaterial());
        assertEquals(20.0f, training1.getTuition(), 0.01f);

        training1.setTuition(500.00f);
        trainingContext.updateTraining(training1);
        Training training2 = trainingContext.getTraining("004");
        assertEquals(500.00f, (float)training2.getTuition(),0.01f);
    }

    @Test
    public void removeTraining() {
        reset();
        trainingContext.addTraining(training);
        assertEquals("Java", trainingContext.getTraining("004").getName());
        trainingContext.removeTraining(training);
        assertEquals(null, trainingContext.getTraining("004"));
    }

    @Test
    public void getTraining() {
        reset();
        trainingContext.addTraining(training);
        assertEquals("Java", trainingContext.getTraining("004").getName());
    }

    @Test
    public void getAll() {
        reset();
        trainingContext.addTraining(training);
        assertEquals(1, trainingContext.getAll().size());
    }

    @Test
    public void getTrainingByCategory() {
        reset();
        trainingContext.addTraining(training);

        List<Training> results = new ArrayList<>();
        results = trainingContext.getTrainingByCategory("Java");
        assertEquals(1, results.size());

        results.clear();

        assertEquals(0, results.size());

        results = trainingContext.getTrainingByCategory("Java");
        assertEquals(1, results.size());
    }

    @Test
    public void getTrainingFrequency() {
        reset();
        trainingContext.addTraining(training);

        Map<String, Integer> results = new TreeMap<>();
        results = trainingContext.getTrainingFrequencies();

        assertEquals(1, results.size());

        assertEquals(1,(int) results.get("Java"));

    }

    private void reset() {
        trainingContext.removeAll();
    }

}