package Controler;

import Model.Cards.Card;
import Model.User;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ProfileControl {
    public static String changePassword(Matcher matcher, User user) {
        matcher.find();
        String oldPassword = matcher.group("oldPassword");
        String newPassword = matcher.group("newPassword");
        if (!oldPassword.equals(user.getPassword())) {
            return "Incorrect password!";
        } else if (!newPassword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\!\\@\\#\\$\\%\\^\\&\\*])\\S{8,20}$")) {
            return "Incorrect format for new password!";
        } else if (newPassword.matches("\\d.*")) {
            return "Incorrect format for new password!";
        } else {
            user.setPassword(newPassword);
            return "Password changed successfully!";
        }
    }

    public static String Info(User user) {
        String s = new String();
        s += ("username: " + user.getUserName() + "\n");
        s += ("password: " + user.getPassword() + "\n");
        s += ("level: " + user.getLevel() + "\n");
        s += ("experience: " + user.getXp() + "\n");
        s += ("gold: " + user.getGold() + "\n");
        s += ("rank: " + user.getRank() + "\n");
        return s;
    }

    public static String removeCard(Matcher matcher, User user) {
        matcher.find();
        String name = matcher.group("name");
        if (!Card.Types.contains(name)) {
            return "Invalid card name!";
        } else if (!user.getBattleDeck().contains(Card.CardMaker(name))) {
            return "This card isn't in your battle deck!";
        } else if (user.getBattleDeck().size() == 1) {
            return "Invalid action: your battle deck will be empty!";
        } else {
            user.removeFromDeck(Card.CardMaker(name));
            return "Card " + name + " removed successfully!";
        }
    }

    public static String addToDeck(Matcher matcher, User user) {
        matcher.find();
        String name = matcher.group("name");
        if (!Card.Types.contains(name)) {
            return "Invalid card name!";
        } else if (!user.getAllCards().contains(Card.CardMaker(name))) {
            return "You don't have this card!";
        } else if (user.getBattleDeck().contains(Card.CardMaker(name))) {
            return "This card is already in your battle deck!";
        } else if (user.getBattleDeck().size() == 4) {
            return "Invalid action: your battle deck is full!";
        } else {
            user.addToDeck(Card.CardMaker(name));
            return "Card " + name + " added successfully!";
        }
    }

    public static String showDeck(User user) {
        String ans = new String();
        for (Card card : user.sortedDeck()) {
            ans += (card.getName() + "\n");
        }
        return ans;
    }
}
