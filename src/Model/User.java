package Model;

import Model.Cards.Barbarian;
import Model.Cards.Card;
import Model.Cards.FireBall;
import Model.Drawables.BattleSide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;

public class User implements Comparable<User>{
    private String userName;
    private String password;
    private int gold;
    private int level;
    private int xp;
    private ArrayList<Card> battleDeck = new ArrayList<>();
    private ArrayList<Card> allCards = new ArrayList<>();
    private int rank;
    private BattleSide side;
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        gold = 100;
        level=1;
        xp = 0;
        battleDeck.add(new Barbarian());
        battleDeck.add(new FireBall());
        allCards.add(new Barbarian());
        allCards.add(new FireBall());
    }

    public void addToDeck(Card card) {
        battleDeck.add(card);
    }

    public ArrayList<Card> sortedDeck() {
        ArrayList<Card> ans = new ArrayList<>(battleDeck);
        Collections.sort(ans);
        return ans;
    }

    public void removeFromDeck(Card card) {
        battleDeck.remove(card);
    }

    public void buyCard(Card card) {
        allCards.add(card);
        gold-=card.getPriceBuy();
    }

    public void sellCard(Card card) {
        allCards.remove(card);
        gold += card.getPriceSell();
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getGold() {
        return gold;
    }

    public int getXp() {
        return xp;
    }

    public ArrayList<Card> getBattleDeck() {
        return battleDeck;
    }

    public int getRank() {
        for (int i = 0; i < Game.ScoreBoard().size(); i++) {
            if (Game.ScoreBoard().get(i).equals(this)) {
                return i + 1;
            }
        }
        return -1;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    @Override
    public int compareTo(User o) {
        if (this.level > o.level) {
            return -1;
        } else if (this.level < o.level) {
            return +1;
        }
        if (this.xp > o.xp) {
            return -1;
        } else if (this.xp < o.xp) {
            return +1;
        }
        return this.userName.compareTo(o.userName);
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void increaseGold(int amount) {
        gold += amount;
    }

    public void increaseLevel() {
        level++;
    }

    public void increaseXP(int amount) {
        xp += amount;
        while (xp >= 160 * level * level) {
            xp -= 160 * level * level;
            level++;
        }
    }

    public BattleSide getSide() {
        return side;
    }

    public void setSide(BattleSide side) {
        this.side = side;
    }
}
