package org.hotelreservation.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hotelreservation.model.Reservation;
import org.hotelreservation.model.Room;
import org.hotelreservation.model.User;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class RoomServiceImpl implements RoomService {

    private final ObjectMapper mapper = new ObjectMapper();
    private List<Room> rooms = new ArrayList<>();
    private ReservationService reservationService;

    public RoomServiceImpl(ReservationService reservationService) {
        this.reservationService = reservationService;
        loadRooms();
    }

    @Override
    public List<Room> getAll() {
        return this.rooms;
    }

    @Override
    public void save() {
        try {
            mapper.writeValue(new File("src/main/resources/rooms.json"), this.rooms);
        } catch (IOException e) {
            throw new RuntimeException(e);
            //TODO
        }
    }

    @Override
    public void viewAll() {
        for (Room room : this.rooms) {
            System.out.println(room);
        }
    }

    @Override
    public void book(User currentUser) {
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Scanner scanner = new Scanner(System.in);

        Reservation currentReservation;
        Room currentRoom;

        System.out.println("Please insert check-in date in the following format: DD-MM-YYYY");
        String checkIn = scanner.nextLine();

        // Validate Check-in date format
        LocalDate checkInRequest = null;
        boolean isCheckInFormatValid = false;
        while (!isCheckInFormatValid) {
            try {
                checkInRequest = LocalDate.parse(checkIn, FORMATTER);
                isCheckInFormatValid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again: ");
                checkIn = scanner.nextLine();
            }
        }

        System.out.println("Please insert check-out date in the following format: DD-MM-YYYY");
        String checkOut = scanner.nextLine();

        // Validate Check-out date format
        LocalDate checkOutRequest = null;
        boolean isCheckOutFormatValid = false;
        while (!isCheckOutFormatValid) {
            try {
                checkOutRequest = LocalDate.parse(checkOut, FORMATTER);
                isCheckOutFormatValid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again: ");
                checkOut = scanner.nextLine();
            }
        }


        validateDates(checkInRequest, checkOutRequest);

        System.out.println("What type of room would you like?");
        printRoomTypes(rooms);
        String roomTypeRequested = scanner.nextLine();
        checkForAvailableRooms(checkInRequest, checkOutRequest, roomTypeRequested, reservationService.getAll(), rooms);

        System.out.println("Which room number did you choose to reserve?");
        int roomNumberReserve = Integer.parseInt(scanner.nextLine());
        currentRoom = getRoomByNumber(roomNumberReserve, rooms);

        if (currentRoom != null) {
            currentReservation = new Reservation(currentRoom.getRoomNumber(), currentUser.getUserName(), checkInRequest, checkOutRequest);
            reservationService.getAll().add(currentReservation);
            reservationService.setCurrentReservation(currentReservation);
            System.out.println("You have successfully created a reservation with ID: " + currentReservation.getReservationId());
        } else {
            System.out.println("There is no room with number: " + roomNumberReserve);
        }


    }

    @Override
    public void cancelBooking(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please insert your reservation ID:");
        String cancelID = scanner.nextLine();

        for (Reservation reservation : reservationService.getAll()) {
            if (cancelID.equals(reservation.getReservationId()) && user.getUserName().equals(reservation.getUserName())) {
                reservation.setStatus("Canceled");
                System.out.println("Your reservation has been canceled.");
                Room cancelRoom = getRoomByNumber(reservation.getRoomNumber(), rooms);
                System.out.println("Your cancellation fee is: " + cancelRoom.getCancellationFee());

            }
        }
    }


    private static void validateDates(LocalDate checkInRequest, LocalDate checkOutRequest) {
        if (checkInRequest.isAfter(checkOutRequest)) {
            System.out.println("Invalid Dates.");
            //TODO
        }
    }

    private static void printRoomTypes(List<Room> rooms) {
        Set<String> roomTypes = new HashSet<>();

        for (Room room : rooms) {
            roomTypes.add(room.getRoomType());
        }

        for (String roomType : roomTypes) {
            System.out.println(roomType);
        }
    }

    private static List<Integer> checkForAvailableRooms(LocalDate checkInRequest, LocalDate checkOutRequest, String roomTypeRequested, List<Reservation> reservations, List<Room> rooms) {
        List<Integer> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getRoomType().equalsIgnoreCase(roomTypeRequested)) {
                boolean isReserved = false;
                for (Reservation reservation : reservations) {

                    if (room.getRoomNumber() == reservation.getRoomNumber() && reservation.getStatus().equals("Confirmed")) {
                        if ((checkInRequest.isAfter(reservation.getCheckIn()) && checkInRequest.isBefore(reservation.getCheckOut()))
                                || (checkOutRequest.isAfter(reservation.getCheckIn()) && checkOutRequest.isBefore(reservation.getCheckOut()))
                                || (checkInRequest.isBefore(reservation.getCheckIn()) && checkOutRequest.isAfter(reservation.getCheckOut()))) {
                            isReserved = true;
                            System.out.println("There are no available rooms.");
                        }
                    }
                }
                if (!isReserved) {
                    availableRooms.add(room.getRoomNumber());
                    System.out.println(room);
                }
            }
        }
        return availableRooms;
    }

    private static Room getRoomByNumber(int roomNumberReserve, List<Room> rooms) {
        for (Room room : rooms) {
            if (roomNumberReserve == room.getRoomNumber()) {
                return room;
            }
        }
        return null;
    }

    private void loadRooms() {
        try {
            this.rooms = mapper.readValue(new File("src/main/resources/rooms.json"), new TypeReference<List<Room>>() {
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
            //TODO
        }
    }

    @Override
    public void calculateProfit() {
        double profit = 0;
        for (Reservation reservation : reservationService.getAll()) {
            Room currentRoom = getRoomByNumber(reservation.getRoomNumber(), rooms);
            if (reservation.getStatus().equals("Confirmed")) {
                profit += currentRoom.getPricePerNight();
            } else if (reservation.getStatus().equals("Canceled")) {
                profit += currentRoom.getCancellationFee();
            }
        }
        System.out.printf("Your total income is: %.2f lv.\n", profit);
    }

    @Override
    public void addRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Room number:");
        int roomNumber = Integer.parseInt(scanner.nextLine());
        while(!isRoomNumberValid(roomNumber)){
            System.out.println("Room number already exists. Please enter another room number: ");
            roomNumber = Integer.parseInt(scanner.nextLine());
        }
        System.out.println("Room type:");
        String roomType = scanner.nextLine();
        System.out.println("Price per night:");
        int pricePerNight = Integer.parseInt(scanner.nextLine());
        System.out.println("Cancellation fee:");
        int cancellationFee = Integer.parseInt(scanner.nextLine());
        System.out.println("Maximum Occupancy:");
        int maxOccupancy = Integer.parseInt(scanner.nextLine());
        System.out.println("Amenities:");
        String amenities = scanner.nextLine();

        Room room = new Room(roomNumber, roomType, pricePerNight, cancellationFee, maxOccupancy, amenities);
        rooms.add(room);
        System.out.println("Room " +roomNumber  + " has been added.");
    }

    @Override
    public void removeRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which room number would you like to remove ?");
        int roomNumber = Integer.parseInt(scanner.nextLine());
        rooms.remove(getRoomByNumber(roomNumber, rooms));
        System.out.println("Room " + roomNumber + " has been removed.");
    }

    @Override
    public void modifyRoom() {

    }
    private boolean isRoomNumberValid(int roomNumber){
        for(Room room : rooms){
            if(roomNumber == room.getRoomNumber()){
                return false;
            }
        }
        return true;
    }
}

