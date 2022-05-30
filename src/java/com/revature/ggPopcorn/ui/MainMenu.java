package com.revature.ggPopcorn.ui;

import com.revature.ggPopcorn.models.Restaurant;
import com.revature.ggPopcorn.models.Review;
import com.revature.ggPopcorn.models.User;
import com.revature.ggPopcorn.services.RestaurantService;
import com.revature.ggPopcorn.services.ReviewService;
import com.revature.ggPopcorn.services.UserService;
import com.revature.ggPopcorn.util.annotations.Inject;

import java.util.List;
import java.util.Scanner;

public class MainMenu implements IMenu {
    @Inject
    private final User user;
    private final UserService userService;
    private final ReviewService reviewService;
    private final RestaurantService restaurantService;

    @Inject
    public MainMenu(User user, UserService userService, ReviewService reviewService, RestaurantService restaurantService) {
        this.user = user;
        this.userService = userService;
        this.reviewService = reviewService;
        this.restaurantService = restaurantService;
    }

    @Override
    public void start() {
        Scanner scan = new Scanner(System.in);

        exit:
        {
            while (true) {
                System.out.println("\n"+drawHorizontalLine("| Welcome to main menu  |", user.getUsername()));
                System.out.println("| Welcome to main menu " + user.getUsername() + " |");
                System.out.println(drawHorizontalLine("| Welcome to main menu  |", user.getUsername()));
                System.out.println("[1] View all restaurants");
                System.out.println("[x] Sign out");

                System.out.print("\nEnter: ");

                switch (scan.nextLine()) {
                    case "1":
                        viewResto();
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

    private void viewResto() {
        Scanner scan = new Scanner(System.in);
        List<Restaurant> restaurants = restaurantService.getAllResto();

        while (true) {
            System.out.println("\n+-------------------------------------------+");
            System.out.println("| Please select a restaurant to see reviews |");
            System.out.println("+-------------------------------------------+");

            for (int i = 0; i < restaurants.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + restaurants.get(i).getName());
            }

            System.out.print("\nEnter: ");
            int input = scan.nextInt() - 1;

            if (input >= 0 && input < restaurants.size()) {
                Restaurant selectedResto = restaurants.get(input);
                List<Review> reviews = reviewService.getReviewByResto(selectedResto.getId());

                System.out.println("\n" + drawHorizontalLine("| Reviews for  |", selectedResto.getName()));
                System.out.println("| Reviews for " + selectedResto.getName() + " |");
                System.out.println(drawHorizontalLine("| Reviews for  |", selectedResto.getName()));

                if (!reviews.isEmpty()) {
                    exit:
                    {
                        while (true) {
                            int newLine = 0;
                            for (Review r : reviews) {
                                User userReview = userService.getUserById(r.getUser_id());
                                System.out.println(r.getMsg() + "\nRating: " + r.getRating() + "\nUser: " + userReview.getUsername());

                                if (newLine < reviews.size() - 1) System.out.println();

                                newLine++;
                            }

                            scan.nextLine();

                            System.out.println("\nDo you want to leave a review? (y/n)");
                            System.out.print("\nEnter: ");

                            switch (scan.nextLine()) {
                                case "y":
                                    break;
                                case "n":
                                    break exit;
                                default:
                                    System.out.println("\nInvalid input!");
                                    break;
                            }
                        }
                    }
                } else {
                    exit:
                    {
                        scan.nextLine();

                        System.out.println("No reviews yet!");
                        System.out.println("\nDo you want to leave a review? (y/n)");
                        System.out.print("\nEnter: ");

                        switch (scan.nextLine()) {
                            case "y":
                                break;
                            case "n":
                                break exit;
                            default:
                                System.out.println("\nInvalid input!");
                                break;
                        }
                    }
                }
            } else {
                System.out.println("\nInvalid restaurant selection.");
            }
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
