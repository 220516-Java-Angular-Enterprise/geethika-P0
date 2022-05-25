package com.revature.project0.ui;

import com.revature.project0.models.User;

public class MainMenu implements IMenu {

//    makes it impossible for user to switch while in the program
    private final User user;

    public MainMenu(User user) {
        this.user = user;
    }


    @Override
    public void start() {
        System.out.println("\nWelcome to my store " + user.getUsername());
    }
}
