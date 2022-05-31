package com.revature.ggPopcorn.ui;

import com.revature.ggPopcorn.daos.*;
import com.revature.ggPopcorn.models.User;
import com.revature.ggPopcorn.services.*;
import com.revature.ggPopcorn.util.annotations.Inject;
import com.revature.ggPopcorn.util.custom_exceptions.InvalidUserException;

import java.util.Scanner;
import java.util.UUID;

public class StartMenu implements IMenu {

    @Inject
    private final UserService userService;

    @Inject
    public StartMenu(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void start() {
        Scanner scan = new Scanner(System.in);

        exit:
        {
            while (true) {
                displayWelcomeMessage();

                /* Asking user to enter in their input. */
                System.out.print("\nEnter: ");
                String input = scan.nextLine();

                /* Switch case, basically if else statement but more simple. */
                switch (input) {
                    /* If the user enters 1, 2, or x. */
                    case "1":
                        /* Call the login() method. */
                        login();
                        break;
                    case "2":
                        /* Call the signup() method. */
                        signup();
                        break;
                    case "x":
                        System.out.println("\nCome back soon!");
                        /* Breaking out of everything. */
                        break exit;
                    default:
                        System.out.println("\nInvalid input.");
                        break;
                }
            }
        }
    }

    private void displayWelcomeMessage() {
        /* Welcome message. */
        System.out.println("| Welcome to GG's Popcorn! |");
        System.out.println("[1] Login");
        System.out.println("[2] Signup");
        System.out.println("[x] Exit");
    }

    private void login() {
        String username;
        String password;
        User user = new User();
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("Enter your login credintials: ");
            System.out.print("Username: ");
            username = scan.nextLine();

            System.out.print("\nPassword: ");
            password = scan.nextLine();

            try {
                user = userService.login(username, password);

                if (user.getRole().equals("ADMIN"))
                    //Admin Login
                    new AdminMenu(user, new StoreService(new StoreDAO()),
                            new InventoryService(new InventoryDAO()),
                            new OrderService(new OrderDAO()),
                            new PopcornService(new PopcornDAO()),
                            new UserService(new UserDAO())).start();
                else
                    //Regular Login
                    new MainMenu(user, new UserService(new UserDAO()),
                            new StoreService(new StoreDAO()),
                            new OrderService(new OrderDAO())
                            new PopcornService(new PopcornDAO())).start();
                break;
            } catch (InvalidUserException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void signup() {
        String username;
        String password;
        Scanner scan = new Scanner(System.in);

        completeExit:
        {
            while (true) {
                System.out.println("Create A New Acount");
                while (true) {
                    System.out.print("\nUsername: ");
                    username = scan.nextLine();

                    try {
                        if (userService.isValidUsername(username)) {
                            if (userService.isNotDuplicateUsername(username)) break;
                        }
                    } catch (InvalidUserException e) {
                        System.out.println(e.getMessage());
                    }
                }


                while (true) {
                    System.out.print("\nPassword: ");
                    password = scan.nextLine();

                    try {
                        if (userService.isValidPassword(password)) {
                            System.out.print("\nRe-enter password: ");
                            String confirm = scan.nextLine();

                            if (password.equals(confirm)) break;
                            else System.out.println("Password does not match");
                        }
                    } catch (InvalidUserException e) {
                        System.out.println(e.getMessage());
                    }
                }

                confirmExit:
                {
                    while (true) {
                        /* Asking user to confirm username and password. */
                        System.out.println("Please confirm your credentials (y/n)");

                        System.out.println("Username: " + username);
                        System.out.println("Password: " + password);

                        System.out.print("\nEnter: ");
                        String input = scan.nextLine();

                        switch (input) {
                            case "y":
                                User user = new User(UUID.randomUUID().toString(), username, password, "DEFAULT");

                                userService.register(user);

                                new MainMenu(user, new UserService(new UserDAO()),
                                        new StoreService(new StoreDAO()),
                                        new OrderService(new OrderDAO())
                                        new PopcornService(new PopcornDAO())).start();
                                break completeExit;
                            case "n":
                                break confirmExit;
                            default:
                                System.out.println("Invalid Input.");
                                break;
                        }
                    }
                }
            }
        }
    }
}
