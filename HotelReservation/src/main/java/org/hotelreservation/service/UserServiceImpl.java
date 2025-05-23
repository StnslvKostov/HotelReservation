package org.hotelreservation.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hotelreservation.model.User;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserServiceImpl implements UserService{

    private final ObjectMapper mapper = new ObjectMapper();
    private User currentUser = null;
    private List<User> users = new ArrayList<>();
    private ReservationService reservationService;

    public UserServiceImpl(ReservationService reservationService) {
        loadUsers();
        this.reservationService = reservationService;
    }

    private void loadUsers() {
        try {
            this.users = mapper.readValue(new File("src/main/resources/users.json"), new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public void save() {
        try {
            mapper.writeValue(new File("src/main/resources/users.json"), users);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void logIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Username: ");
        String username = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        User currentUser = null;
        for (User user : users) {
            if (username.equalsIgnoreCase(user.getUserName()) && password.equals(user.getPassword())) {
                currentUser = user;
                System.out.println("Welcome to the Hotel Management experience 2025, hardcore SirmaAcademy Task");
            }
        }
        if (currentUser == null) {
            System.out.println("Wrong username or password.");
            System.exit(0);
        }
        this.currentUser = currentUser;
    }

    @Override
    public void signUp() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Username: ");
        String username = scanner.nextLine();

        boolean userCheck = userExist(users, username);
        while (userCheck) {
            username = scanner.nextLine();
            userCheck = userExist(users, username);
        }
        System.out.println("Password: ");
        String password = scanner.nextLine();


        System.out.println("You have successfully created a profile.");
        User user = new User(username, password);
        users.add(user);

        this.currentUser = user;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void addHistory(String msg) {
        currentUser.getHistory().add(LocalDateTime.now() + ": " + msg);
    }

    @Override
    public void viewProfile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome " + currentUser.getUserName());
        System.out.println("1. View history\n2. View reservation");
        String profileAction = scanner.nextLine();
        if (profileAction.equals("1")) {
            printHistory(currentUser.getHistory());
        } else {
            reservationService.printByUser(currentUser);
        }
    }

    private static boolean userExist(List<User> users, String username) {
        for (User user : users) {
            if (username.equalsIgnoreCase(user.getUserName())) {
                System.out.println("This username is already taken\nUsername: ");
                return true;
            }
        }
        return false;
    }
    private static void printHistory(List<String> history) {
        for (String currentHistory : history) {
            System.out.println(currentHistory);
        }
    }
}
