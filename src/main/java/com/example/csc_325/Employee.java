package com.example.csc_325;

public class Employee extends User {
    private String employeeId;
    private String jobTitle;

    public Employee(String userId, String name, String email, String employeeId, String jobTitle) {
        super(userId, name, email);
        this.employeeId = employeeId;
        this.jobTitle = jobTitle;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    @Override
    public String getUserType() {
        return "Employee";
    }
}
