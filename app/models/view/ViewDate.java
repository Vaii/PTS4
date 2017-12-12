package models.view;

import models.storage.Location;
import models.storage.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewDate implements Comparable<ViewDate> {
    private String dateId;
    private Date date;
    private Location location;
    private User teacher;
    private int signUps;
    private List<User> trainees;
    private boolean currentUserSignedUp = false;

    public ViewDate(String dateId, Date date, Location location, User teacher, int signUps, List<User> traineesIds) {
        this.dateId = dateId;
        this.date = date;
        this.location = location;
        this.teacher = teacher;
        this.signUps = signUps;
        this.trainees = traineesIds;
    }

    public ViewDate(String dateId, Date date, Location location, User teacher, int signUps) {
        this.dateId = dateId;
        this.date = date;
        this.location = location;
        this.teacher = teacher;
        this.signUps = signUps;
    }

    public ViewDate(String dateId, Date date, Location location, User teacher, int signUps, boolean currentUserSignedUp) {
        this.dateId = dateId;
        this.date = date;
        this.location = location;
        this.teacher = teacher;
        this.signUps = signUps;
        this.currentUserSignedUp = currentUserSignedUp;
    }

    public Date getDate() {
        return date;
    }

    /*
     * Format date to string that is uneditable.
     */
    public String getDateString() {
        DateFormat df = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        return df.format(date);
    }

    public String getDateOnlyString() {
        DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy", Locale.ENGLISH);
        return df.format(date);
    }

    public String getTmeOnlyString() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(date);
    }
    /*
     * Use for edit boxes in view.
     */
    public String getEditableString() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        return df.format(date);
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

    public int getSignUps() {
        return signUps;
    }

    public void setSignUps(int signUps) {
        this.signUps = signUps;
    }

    public List<User> getTrainees() {
        return trainees;
    }

    public void setTrainees(List<User> trainees) {
        this.trainees = trainees;
    }

    public boolean isCurrentUserSignedUp() {
        return currentUserSignedUp;
    }

    public void setCurrentUserSignedUp(boolean currentUserSignedUp) {
        this.currentUserSignedUp = currentUserSignedUp;
    }

    @Override
    public int compareTo(ViewDate other) {
        return this.date.compareTo(other.getDate());
    }
}
