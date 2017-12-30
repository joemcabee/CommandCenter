package com.pop.commandcenter.models;

import java.util.Date;

/**
 * Created by joemc on 12/27/2017.
 */

public class Temperature {
    public double getFahrenheit() {
        return Fahrenheit;
    }

    public void setFahrenheit(double fahrenheit) {
        Fahrenheit = fahrenheit;
    }

    public double getCelsius() {
        return Celsius;
    }

    public void setCelsius(double celsius) {
        Celsius = celsius;
    }

    public Date getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Date timestamp) {
        Timestamp = timestamp;
    }

    private double Fahrenheit;
    private double Celsius;
    private Date Timestamp;
}
