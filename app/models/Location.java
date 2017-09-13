package models;

import org.jongo.marshall.jackson.oid.MongoObjectId;

public class Location {
    @MongoObjectId
    private int _id;
    private String City, StreetNumber, Room;
    private int Capacity;

    public Location(int _id, String city, String streetNumber, String room, int capacity) {
        this._id = _id;
        City = city;
        StreetNumber = streetNumber;
        Room = room;
        Capacity = capacity;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getStreetNumber() {
        return StreetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        StreetNumber = streetNumber;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }
}

