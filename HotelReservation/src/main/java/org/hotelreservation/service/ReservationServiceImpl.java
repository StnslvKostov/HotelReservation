package org.hotelreservation.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hotelreservation.model.Reservation;
import org.hotelreservation.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    private final ObjectMapper mapper = new ObjectMapper();
    private List<Reservation> reservations = new ArrayList<>();
    private Reservation currentReservation;

    public ReservationServiceImpl() {
        mapper.registerModule(new JavaTimeModule());
        loadReservations();
    }

    @Override
    public List<Reservation> getAll() {
        return reservations;
    }

    @Override
    public void save() {
        try {
            mapper.writeValue(new File("src/main/resources/reservations.json"), this.reservations);
        } catch (IOException e) {
            throw new RuntimeException(e);
            //TODO
        }
    }

    @Override
    public void printByUser(User currentUser) {

        for (Reservation reservation : reservations) {
            if (currentUser.getUserName().equals(reservation.getUserName())) {
                System.out.println(reservation);
            }
        }
    }

    @Override
    public void setCurrentReservation(Reservation currentReservation) {
        this.currentReservation = currentReservation;
    }

    @Override
    public Reservation getCurrentReservation() {
        return currentReservation;
    }

    @Override
    public void printAllConfirmed() {
        for(Reservation reservation : reservations){
            if (reservation.getStatus().equals("Confirmed")){
                System.out.println(reservation);
            }
        }
    }

    private void loadReservations() {
        try {
            this.reservations = mapper.readValue(new File("src/main/resources/reservations.json"), new TypeReference<List<Reservation>>() {
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
            //TODO
        }
    }
}
