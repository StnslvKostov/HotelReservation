package org.hotelreservation.model;

import java.time.LocalDate;
import java.util.UUID;

public class Reservation {
    private int roomNumber;
    private String reservationId;
    private String userName;
    private String status;
    private LocalDate checkIn;
    private LocalDate checkOut;

    public Reservation(){

    }
    public Reservation(int roomNumber, String userName, LocalDate checkIn, LocalDate checkOut) {
        this.roomNumber = roomNumber;
        this.reservationId = UUID.randomUUID().toString();
        this.userName = userName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = "Confirmed";
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return
                String.format("""
                        Reservation ID: %s
                        Room Number: %d
                        Check in: %s
                        Check out: %s
                        """, this.reservationId, this.roomNumber, this.checkIn,this.checkOut);
    }
}
