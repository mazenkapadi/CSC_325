package com.example.csc_325;

public abstract class User {
    private String userId;
    private String name;
    private String email;


    // Constructor
    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Abstract method to be implemented by child classes
    public abstract String getUserType();
}
