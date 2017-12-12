package models.view;

import models.storage.Category;
import models.storage.Training;
import models.util.DateConverter;

public class ViewTraining implements Comparable<ViewTraining> {
        private Training training;
        private ViewDate date;
        private Category category;

        public ViewTraining(Training training, ViewDate date, Category category) {
                this.training = training;
                this.date = date;
                this.category = category;
        }

        public ViewTraining(Training training, Category category) {
                this.training = training;
                this.category = category;
        }

        public Training getTraining() {
                return training;
        }

        public ViewDate getDate() {
                return date;
        }

        public Category getCategory() {
                return category;
        }
        @Override
        public int compareTo(ViewTraining o) {
                return date.getDate().compareTo(o.getDate().getDate());
        }
}

