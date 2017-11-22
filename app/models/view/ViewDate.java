package models.view;

import models.storage.Location;
import models.storage.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;

public class ViewDate {
    private String dateId;
    private LocalDateTime date;
    private Location location;
    private User teacher;

    public ViewDate() {};

    public ViewDate(String dateId, LocalDateTime date, Location location, User teacher) {
        this.dateId = dateId;
        this.date = date;
        this.location = location;
        this.teacher = teacher;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDateString() {
        //DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        TemporalAccessor ta = f.parse(this.date.toString());
        return ta.toString();
    }

    public Location getLocation() {
        return location;
    }

    public User getTeacher() {
        return teacher;
    }

    public String getDateId() {
        return dateId;
    }

}
