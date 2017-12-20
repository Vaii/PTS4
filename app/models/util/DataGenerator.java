package models.util;

import dal.contexts.CategoryContext;
import dal.contexts.DateTimeMongoContext;
import dal.contexts.TrainingMongoContext;
import dal.repositories.CategoryRepository;
import dal.repositories.DateTimeRepository;
import dal.repositories.TrainingRepository;
import models.storage.Training;

import java.util.ArrayList;

public class DataGenerator {
    static TrainingRepository trainingRepo = new TrainingRepository(new TrainingMongoContext("Training"));
    CategoryRepository catRepo = new CategoryRepository(new CategoryContext("Category"));
    DateTimeRepository dateRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));

    public static void main(String[] args) {
        generateTrainings(100, 0);
    }

    private static void generateTrainings(int trainingAmount, int dateAmount) {
        for(int i = 0; i <= trainingAmount; i++) {
            Training t = new Training(Integer.toString(i), ("Training: " + Integer.toString(i)), "Test training", "Fiets", 5f, 100, 20000, new ArrayList<String>(), "5a2fe5f7d8269f2984d6bdc1", new ArrayList<String>());
            trainingRepo.addTraining(t);
        }

    }
}
