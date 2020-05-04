package com.example.model;

public class LoginResponse {

    private boolean error;
    private String message;
    private int success;
    private User login;

    public LoginResponse(boolean error, String message, int success, User login) {
        this.error = error;
        this.message = message;
        this.success = success;
        this.login = login;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public int getSuccess() {
        return success;
    }

    public User getLogin() {
        return login;
    }
}
