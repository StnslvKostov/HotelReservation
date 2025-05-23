package org.hotelreservation.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userName;
    private String password;
    private String role;
    private List<String> history = new ArrayList<>();

    public User() {
    }

    public User(String username, String password) {
        this.userName = username;
        this.password = password;
        this.role = "user";
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
