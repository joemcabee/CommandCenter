package com.pop.commandcenter.models;

/**
 * Created by joemc on 7/22/2017.
 */

public class ServiceResponse {
    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public Boolean getSuccess() {
        return Success;
    }

    public void setSuccess(Boolean success) {
        Success = success;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public java.lang.Exception getException() {
        return Exception;
    }

    public void setException(java.lang.Exception exception) {
        Exception = exception;
    }

    String Service;
    Boolean Success;
    int StatusCode;
    String Data;
    Exception Exception;
}
