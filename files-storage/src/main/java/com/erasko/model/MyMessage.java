package com.erasko.model;

public class MyMessage {
    private String message;
    private String fileName;

    public MyMessage(String message, String fileName) {
        this.message = message;
        this.fileName = fileName;
    }

    public MyMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
