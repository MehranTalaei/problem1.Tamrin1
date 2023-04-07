package Controler;

import Model.Cards.Card;
import Model.User;

import java.util.regex.Matcher;

public class ShopControl {
    public static String buyCard(Matcher matcher, User user) {
        matcher.find();
        String name = matcher.group("name");
        if (!Card.Types.contains(name)) {
            return "Invalid card name!";
        } else if (Card.CardMaker(name).getPriceBuy() > user.getGold()) {
            return "Not enough gold to buy " + name + "!";
        } else if (user.getAllCards().contains(Card.CardMaker(name))) {
            return "You have this card!";
        } else {
            user.buyCard(Card.CardMaker(name));
            return "Card " + name + " bought successfully!";
        }
    }

    public static String sellCard(Matcher matcher, User user) {
        matcher.find();
        String name = matcher.group("name");
        if (!Card.Types.contains(name)) {
            return "Invalid card name!";
        } else if (!user.getAllCards().contains(Card.CardMaker(name))) {
            return "You don't have this card!";
        } else if (user.getBattleDeck().contains(Card.CardMaker(name))) {
            return "You cannot sell a card from your battle deck!";
        } else {
            user.sellCard(Card.CardMaker(name));
            return "Card " + name + " sold successfully!";
        }
    }

}
