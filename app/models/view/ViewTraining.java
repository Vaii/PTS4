package models.view;

import models.storage.DateTime;
import models.storage.Location;
import models.storage.Training;

public class ViewTraining {
        Training training;
        Location location;
        DateTime date;

        public ViewTraining(Training training, Location location, DateTime date) {
                this.training = training;
                this.location = location;
                this.date = date;
        }
        public Training getTraining() {
                return training;
        }

        public Location getLocation() {
                return location;
        }

        public DateTime getDate() {
                return date;
        }


}

