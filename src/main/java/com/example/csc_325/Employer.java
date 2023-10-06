package com.example.csc_325;

public class Employer extends User {
    private String employerId;
    private String companyName;

    public Employer(String userId, String name, String email, String employerId, String companyName) {
        super(userId, name, email);
        this.employerId = employerId;
        this.companyName = companyName;
    }

    public String getEmployerId() {
        return employerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String getUserType() {
        return "Employer";
    }
}
