package org.hotelreservation;

import org.hotelreservation.model.User;
import org.hotelreservation.service.*;

import java.util.Scanner;


public class HotelManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ReservationService reservationService = new ReservationServiceImpl();
        RoomService roomService = new RoomServiceImpl(reservationService);
        UserService userService = new UserServiceImpl(reservationService);

        loginMenuActionHandler(scanner, userService);
        mainMenuActionHandler(scanner, userService, reservationService, roomService);

        userService.save();
        roomService.save();
        reservationService.save();
    }

    private static void loginMenuActionHandler(Scanner scanner, UserService userService) {
        System.out.println("1. Log in");
        System.out.println("2. Sign up");
        String logInSignUp = scanner.nextLine();

        switch (logInSignUp) {
            case "1" -> {
                userService.logIn();
            }

            case "2" -> {
                userService.signUp();
                userService.addHistory("Account has been created");
            }
        }
        printMenu(userService.getCurrentUser());
    }

    private static void mainMenuActionHandler(Scanner scanner, UserService userService, ReservationService reservationService, RoomService roomService) {
        String action = scanner.nextLine();
        if(userService.getCurrentUser().getRole().equals("Admin")){
            while (!action.equals("5")) {
                switch (action) {
                    case "1" -> reservationService.printAllConfirmed();
                    case "2" -> roomService.calculateProfit();
                    case "3" -> roomService.addRoom();
                    case "4" -> roomService.removeRoom();
                }
                printMenu(userService.getCurrentUser());
                action = scanner.nextLine();
            }
        }else{
            while (!action.equals("5")) {

                switch (action) {
                    case "1" -> {
                        roomService.viewAll();
                    }
                    case "2" -> {
                        roomService.book(userService.getCurrentUser());
                        userService.addHistory("You have successfully created a reservation with ID: " + reservationService.getCurrentReservation().getReservationId());
                    }
                    case "3" -> {
                        roomService.cancelBooking(userService.getCurrentUser());
                        userService.addHistory("Your reservation has been canceled. Reservation ID: " + reservationService.getCurrentReservation().getReservationId());
                    }
                    case "4" -> {
                        userService.viewProfile();
                    }
                }
                printMenu(userService.getCurrentUser());
                action = scanner.nextLine();
            }
        }
    }

    private static void printMenu(User currentUser) {
        if (currentUser.getRole().equals("Admin")) {
            System.out.println("1. View all bookings.\n2. View total income and cancellation fees.\n3. Add Room\n4. Remove Room\n5. Exit");
        } else {
            System.out.println("1. View Rooms\n2. Book a Room\n3. Cancel Booking\n4. View Profile\n5. Exit");
        }
    }

}
