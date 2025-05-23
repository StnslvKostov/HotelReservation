package org.hotelreservation.service;

import org.hotelreservation.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();
    void save();
    void logIn();
    void signUp();
    User getCurrentUser();
    void addHistory(String msg);
    void viewProfile();

}
