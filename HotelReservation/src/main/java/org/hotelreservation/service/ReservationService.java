package org.hotelreservation.service;

import org.hotelreservation.model.Reservation;
import org.hotelreservation.model.User;

import java.util.List;

public interface ReservationService {


    List<Reservation> getAll();
    void save();
    void printByUser(User currentUser);
    void setCurrentReservation(Reservation currentReservation);
    Reservation getCurrentReservation();
    void printAllConfirmed();

}
