package org.hotelreservation.service;

import org.hotelreservation.model.Room;
import org.hotelreservation.model.User;

import java.util.List;

public interface RoomService {

    List<Room> getAll();
    void save();
    void viewAll();
    void book(User currentUser);
    void cancelBooking(User currentUser);
    void calculateProfit();
    void addRoom();
    void removeRoom();
    void modifyRoom();

}
