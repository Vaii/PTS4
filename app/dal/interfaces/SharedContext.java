package dal.interfaces;

import models.storage.Training;

import java.util.List;
import java.util.Map;

public interface SharedContext {
    /**
     * Get a list containing all trainings a user has signed up for.
     * The exact dateTime still needs to be find manually.
     * @param userId the id of the user.
     * @return A list of trainings the user signed up for.
     */
    List<Training> getTrainings(String userId);

    /**
     * Remove a training and all associated date time objects.
     * @param trainingCode The training code of the training.
     * @return True if the deletion was successful.
     */
    Boolean removeTraining(String trainingCode);

    /**
     * Get a list containing all trainings a teacher is signed up for.
     * The exact dateTime still needs to be find manually.
     * @param userId the id of the teacher.
     * @return A list of trainings the teacher signed up for.
     */
    List<Training> getTrainingsForTeacher(String userId);


    /**
     * Get a list containing all trainings a user is signed up for including category.
     * @param userId the id of the teacher.
     * @return A list of categories and size.
     */
    Map<String, Integer> getTrainingFrequencies();
}
