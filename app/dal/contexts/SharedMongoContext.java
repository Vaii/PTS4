package dal.contexts;

import dal.interfaces.SharedContext;
import dal.repositories.DateTimeRepository;
import dal.repositories.TrainingRepository;
import models.storage.DateTime;
import models.storage.Training;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SharedMongoContext implements SharedContext {

    private TrainingRepository trainingRepo = new TrainingRepository(new TrainingMongoContext("Training"));
    private DateTimeRepository dateRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));

    @Override
    public List<Training> getTrainings(String userID) {
        return new ArrayList<>();
    }

    @Override
    public Boolean removeTraining(String trainingCode) {
        Training t = trainingRepo.getTraining(trainingCode);
        for(String dt : t.getDateIds()) {
            dateRepo.removeDateTime(dt);
        }
        return trainingRepo.removeTraining(t);
    }

    @Override
    public List<Training> getTrainingsForTeacher(String userId) {
        List<Training> trainings;
        List<Training> results = new ArrayList<>();
        trainings = trainingRepo.getAll();

        for(Training t : trainings) {
            for(String id : t.getDateIds()) {
                DateTime date = dateRepo.getDateTime(id);
                if(date.getTeacherID().equals(userId)) {
                    results.add(t);
                }
            }
        }

        return results;
    }
    @Override
    public Map<String, Integer> getTrainingFrequencies(String id) {
        Map<String, Integer> results = new TreeMap<>();
        List<Training> trainings;
        trainings = getTrainings(id);

        for(Training t : trainings) {
            if(!results.containsKey(t.getCategory())) {
                results.put(t.getCategory(), 1);
            } else {
                results.put(t.getCategory(), results.get(t.getCategory())+ 1);
            }
        }

        return results;
    }
}
