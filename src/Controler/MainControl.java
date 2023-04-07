package Controler;

import Model.Game;
import Model.User;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MainControl {
    public static String ShowListOfUsers() {
        String ans = new String();
        int counter=1;
        for (User allUser : Game.getAllUsers()) {
            ans+=("user " + counter + ": " + allUser.getUserName());
            ans += "\n";
            counter++;
        }
        return ans;
    }

    public static String ShowScoreBoard() {
        ArrayList<User> sorted=Game.ScoreBoard();
        String ans = new String();
        for (int i = 0; i < sorted.size(); i++) {
            if (i > 4) {
                break;
            }
            User currentUser = sorted.get(i);
            ans += ((i + 1) + "- username: " + currentUser.getUserName() + " level: " +
                    currentUser.getLevel() + " experience: " + currentUser.getXp() + "\n");
        }
        return ans;
    }

    public static String startNewGame(Matcher matcher) {
        matcher.find();
        String username = matcher.group("username");
        int turnsCount = Integer.parseInt(matcher.group("turnCount"));
        if (turnsCount > 30 || turnsCount < 5) {
            return "Invalid turns count!";
        } else if (!username.matches("[a-zA-Z]+")) {
            return "Incorrect format for username!";
        } else if (Game.getUserByName(username) == null) {
            return "Username doesn't exist!";
        } else {
            return "Battle started with user " + username;
        }
    }
}
