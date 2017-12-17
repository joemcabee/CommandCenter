package com.pop.commandcenter.models;

/**
 * Created by joemc on 7/22/2017.
 */

public class ServiceRequest {

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String toString() {
        String value = getUrl();

        if (value.endsWith("/")) {
            value += getService();
        }
        else {
            value += "/" + getService();
        }

        return value;
    }

    String Url;
    String Service;
}
