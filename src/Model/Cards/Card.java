package Model.Cards;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

abstract public class Card implements Comparable<Card>{
    protected String name;
    protected int damage;
    protected int priceBuy;
    protected int priceSell;
    public static Set<String> Types;
    static {
        Set<String> buff = new HashSet<>();
        buff.add("Fireball");
        buff.add("Heal");
        buff.add("Barbarian");
        buff.add("Baby Dragon");
        buff.add("Ice Wizard");
        Types=buff;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return name.equals(((Card) o).name);
    }

    public static Card CardMaker(String input) {
        if (input.equals("Fireball")) {
            return new FireBall();
        }
        if (input.equals("Heal")) {
            return new Heal();
        }
        if (input.equals("Barbarian")) {
            return new Barbarian();
        }
        if (input.equals("Baby Dragon")) {
            return new BabyDragon();
        }
        if (input.equals("Ice Wizard")) {
            return new IceWizard();
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getPriceBuy() {
        return priceBuy;
    }

    public int getPriceSell() {
        return priceSell;
    }

    @Override
    public int compareTo(Card o) {
        return this.name.compareTo(o.name);
    }
}
