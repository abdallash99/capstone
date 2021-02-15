package com.capstone.project.game;

public class ResponseInfo {
    private String message;

    public ResponseInfo(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseInfo{" +
                "message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMassage(String message) {
        this.message = message;
    }
}
