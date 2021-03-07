package com.lean.cassandra.bean;

public class JsonResult {
    private String status = null;
    private Object result = null;

    public JsonResult status(String status) {
        this.status = status;
        return this;
    }

    public void setResult(Object person) {
        this.result = person;
    }

    public Object getResult() {
        return this.result;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}