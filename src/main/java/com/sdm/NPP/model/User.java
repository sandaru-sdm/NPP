package com.sdm.NPP.model;

public class User {
    private String name;
    private String username;
    private String password;
    private String mobileNumber;
    private String role;
    private Boolean isActive;

    public User() {}

    public User(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public User(String name, String username, String password, String mobileNumber, String role, Boolean isActive) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.role = role;
        this.isActive = isActive;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}