package com.revature.ggPopcorn.ui;

import com.revature.ggPopcorn.models.Restaurant;
import com.revature.ggPopcorn.models.User;
import com.revature.ggPopcorn.services.RestaurantService;
import com.revature.ggPopcorn.util.annotations.Inject;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class AdminMenu implements IMenu {
    @Inject
    private final User user;
    private final RestaurantService restaurantService;

    @Inject
    public AdminMenu(User user, RestaurantService restaurantService) {
        this.user = user;
        this.restaurantService = restaurantService;
    }

    @Override
    public void start() {
        Scanner scan = new Scanner(System.in);

        exit:
        {
            while (true) {
                System.out.println("\n" + drawHorizontalLine("| Welcome to admin menu  |", user.getUsername()));
                System.out.println("| Welcome to admin menu " + user.getUsername() + " |");
                System.out.println(drawHorizontalLine("| Welcome to admin menu  |", user.getUsername()));
                System.out.println("[1] Create restaurant");
                System.out.println("[2] Update restaurant");
                System.out.println("[3] Delete restaurant");
                System.out.println("[4] Search restaurant");
                System.out.println("[x] Sign out");

                System.out.print("\nEnter: ");

                switch (scan.nextLine()) {
                    case "1":
                        createRestaurant();
                        break;
                    case "2":
                        updateRestaurant();
                        break;
                    case "3":
                        deleteRestaurant();
                        break;
                    case "4":
                        break;
                    case "x":
                        break exit;
                    default:
                        System.out.println("\nInvalid input.");
                        break;
                }
            }
        }
    }

    private void createRestaurant() {
        Scanner scan = new Scanner(System.in);
        Restaurant resto = new Restaurant();

        exit:
        {
            while (true) {
                System.out.println("\n+------------------------+");
                System.out.println("| Creating restaurant... |");
                System.out.println("+------------------------+");

                resto.setId(UUID.randomUUID().toString());

                System.out.print("Name: ");
                resto.setName(scan.nextLine());

                System.out.print("\nCity: ");
                resto.setCity(scan.nextLine());

                System.out.print("\nState: ");
                resto.setState(scan.nextLine());

                System.out.println("\nPlease confirm restaurant (y/n)");
                System.out.println("\n" + resto);

                switch (scan.nextLine()) {
                    case "y":
                        restaurantService.register(resto);
                        break exit;
                    case "n":
                        break;
                    default:
                        System.out.println("\nInvalid input.");
                        break;
                }
            }
        }
    }

    private void updateRestaurant() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("\n+-------------------------------------------+");
            System.out.println("| Please select a restaurant to update name |");
            System.out.println("+-------------------------------------------+");
            List<Restaurant> restaurants = restaurantService.getAllResto();

            for (int i = 0; i < restaurants.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + restaurants.get(i).getName());
            }

            System.out.print("\nEnter: ");
            int input = scan.nextInt() - 1;
            scan.nextLine();

            if (input >= 0 && input < restaurants.size()) {
                Restaurant selectedResto = restaurants.get(input);

                System.out.print("\nNew name: ");
                String name = scan.nextLine();

                if (restaurantService.updateName(name, selectedResto.getId())) {
                    System.out.println("\nUpdate was successful!");
                    break;
                }
            } else System.out.println("\nInvalid restaurant selection!");
        }
    }

    private void deleteRestaurant() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("\n+--------------------------------------+");
            System.out.println("| Please select a restaurant to delete |");
            System.out.println("+--------------------------------------+");
            List<Restaurant> restaurants = restaurantService.getAllResto();

            for (int i = 0; i < restaurants.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + restaurants.get(i).getName());
            }

            System.out.print("\nEnter: ");
            int input = scan.nextInt() - 1;
            scan.nextLine();

            if (input >= 0 && input < restaurants.size()) {
                Restaurant selectedResto = restaurants.get(input);

                if (restaurantService.deleteResto(selectedResto.getId())) {
                    System.out.println("\nDeletion was successful!");
                    break;
                }
            } else System.out.println("\nInvalid restaurant selection!");
        }
    }

    private String drawHorizontalLine(String msg, String nameLength) {
        int len = msg.length() + nameLength.length();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            if (i == 0 || i == len - 1) sb.append("+");
            else sb.append("-");
        }

        return sb.toString();
    }
}
