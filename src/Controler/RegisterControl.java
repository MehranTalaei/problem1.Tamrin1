package Controler;

import Model.Game;
import Model.User;

import java.util.regex.Matcher;

public class RegisterControl {
    public static String register(Matcher matcher) {
        matcher.find();
        String username = matcher.group("username");
        String password = matcher.group("password");
        if (!username.matches("[a-zA-Z]+")) {
            return "Incorrect format for username!";
        } else if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\!\\@\\#\\$\\%\\^\\&\\*])\\S{8,20}$")) {
            return "Incorrect format for password!";
        } else if (password.matches("\\d.*")) {
            return "Incorrect format for password!";
        } else if (Game.getUserByName(username) != null) {
            return "Username already exists!";
        } else {
            Game.addUser(new User(username,password));
            return "User " + username + " created successfully!";
        }
    }

    public static String login(Matcher matcher) {
        matcher.find();
        String username = matcher.group("username");
        String password = matcher.group("password");
        if (!username.matches("[a-zA-Z]+")) {
            return "Incorrect format for username!";
        } else if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\!\\@\\#\\$\\%\\^\\&\\*])\\S{8,20}$")) {
            return "Incorrect format for password!";
        } else if (password.matches("\\d.*")) {
            return "Incorrect format for password!";
        } else if (Game.getUserByName(username) == null) {
            return "Username doesn't exist!";
        } else if (!Game.getUserByName(username).getPassword().equals(password)) {
            return "Password is incorrect!";
        } else {
            return "User " + username + " logged in!";
        }
    }
}
