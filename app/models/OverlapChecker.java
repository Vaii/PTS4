package models;

import dal.contexts.DateTimeMongoContext;
import dal.contexts.SharedMongoContext;
import dal.repositories.DateTimeRepository;
import dal.repositories.SharedRepository;

import java.util.ArrayList;
import java.util.List;

public class OverlapChecker {
    private SharedRepository sharedRepo = new SharedRepository(new SharedMongoContext());
    private DateTimeRepository dateRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));
    private String userId;

    public OverlapChecker(String userId) {
        this.userId = userId;
    }


    public boolean checkOverlap(DateTime other) {
        List<Training> trainings = new ArrayList<>();
        trainings =  sharedRepo.getTrainingsForTeacher(userId);

        return false;
    }
}
