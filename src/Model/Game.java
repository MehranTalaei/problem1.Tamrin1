package Model;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private static ArrayList<User> allUsers = new ArrayList<>();

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void addUser(User user) {
        allUsers.add(user);
    }

    public static User getUserByName(String username) {
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getUserName().equals(username)) {
                return allUsers.get(i);
            }
        }
        return null;
    }
    public static ArrayList<User> ScoreBoard() {
        ArrayList<User> ans = new ArrayList<>(allUsers);
        Collections.sort(ans);
        return ans;
    }
}
