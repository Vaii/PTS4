package dal.interfaces;

import models.Training;

import java.util.List;

public interface SharedContext {
    List<Training> getTrainings(String userId);
}
