package com.revature.project0;

//allows us to use files in different packages
import com.revature.project0.services.UserService;
import com.revature.project0.ui.StartMenu;
import com.revature.project0.daos.UserDAO;

//This Starts our application and helps us navigate

public class MainDriver {
    public static void main(String[] args) {
//        creates a new userService model so that it is accessible in this file and in subsequent methods
//        anonymous function: function will disappear after the start method executes
        new StartMenu(new UserService(new UserDAO())).start();
    }
}
