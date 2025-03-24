package org.series.website.jakarta.model;


public class Message {
    private String status;
    private Series data;

    public Message() {}

    public Message(String status, Series data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Series getData() {
        return data;
    }

    public void setData(Series data) {
        this.data = data;
    }
}

