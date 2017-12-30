package com.pop.commandcenter.models;

/**
 * Created by joemc on 12/26/2017.
 */

public class Sensor {
    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public String Type;
    public String Location;
    public String Value;
    public String Service;
    public int Position;
}
