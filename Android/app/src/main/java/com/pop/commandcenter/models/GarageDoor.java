package com.pop.commandcenter.models;

/**
 * Created by joemc on 7/27/2017.
 */

public class GarageDoor {
    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public String getDoorSide() {
        return DoorSide;
    }

    public void setDoorSide(String doorSide) {
        DoorSide = doorSide + " Garage Door";
    }

    public String getCar() {
        return Car;
    }

    public void setCar(String car) {
        Car = car;
    }

    String Service;
    String DoorSide;
    String Car;
}
