package com.revature.project0.daos;

import com.revature.project0.models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements CrudDAO<User> {
    String path = "src/main/resources/database/user.txt";

    @Override
    public void save(User obj) {
        try {
            File file = new File(path);
            FileWriter fw = new FileWriter(file, true);
            fw.write(obj.toFileString());
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException("An error occurred when writing to a file.");
        }
    }

    @Override
    public void update(User obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public User getById(String id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    public List<String> getAllUsernames() {
        List<String> usernames = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            String userData; // id:username:password:role
            while ((userData = br.readLine()) != null) {
                String[] userArr = userData.split(":"); // [id, username, password, role]
                String id = userArr[0];
                String username = userArr[1];
                String password = userArr[2];
                String role = userArr[3];

                usernames.add(username);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurred when trying to access the file.");
        } catch (IOException e) {
            throw new RuntimeException("An error occurred when trying to access the file information.");
        }

        return usernames;
    }

    public User getUserByUsernameAndPassword(String un, String pw) {
        User user = new User();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            String userData; // id:username:password:role
            while ((userData = br.readLine()) != null) {
                String[] userArr = userData.split(":"); // [id, username, password, role]
                String id = userArr[0];
                String username = userArr[1];
                String password = userArr[2];
                String role = userArr[3];

                if (un.equals(username)) {
                    user.setId(id);
                    user.setUsername(username);
                    user.setRole(role);

                    if (pw.equals(password)) user.setPassword(password);
                    else break;
                } else if (pw.equals(password)) user.setPassword(password);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurred when trying to access the file.");
        } catch (IOException e) {
            throw new RuntimeException("An error occurred when trying to access the file information.");
        }

        return user;
    }
}
