package models.view;

import models.storage.DateTime;
import models.storage.Location;
import models.storage.Training;
import models.util.DateConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ViewTraining implements Comparable<ViewTraining> {
        Training training;
        Location location;
        DateTime date;

        public ViewTraining(Training training, Location location, DateTime date) {
                this.training = training;
                this.location = location;
                this.date = date;
        }

        public ViewTraining(Training training, DateTime date){
            this.training = training;
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
        public ViewDate getViewDate (){
                DateConverter dateConverter = new DateConverter();
                ViewDate viewDate = dateConverter.convert(date);
                return viewDate;
        }
        @Override
        public int compareTo(ViewTraining o) {
                return this.date.getDate().compareTo(o.getDate().getDate());
        }
}

