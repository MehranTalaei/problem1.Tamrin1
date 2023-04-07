package Model;
import Model.Cards.Card;
import Model.Cards.Heal;
import Model.Cards.Troop;
import Model.Drawables.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Battle {
    HashMap<String,ArrayList<drawable> []>field=new HashMap<>();
    {
        field.put("middle", new ArrayList[17]);
        field.put("right", new ArrayList[17]);
        field.put("left", new ArrayList[17]);
        for (String s : field.keySet()) {
            for (int i = 0; i < field.get(s).length; i++) {
                field.get(s)[i] = new ArrayList<drawable>();
            }
        }
    }
    int movesLeft = 3;
    int attacksLeft = 1;
    private int turn;
    private User Host;
    private User Guest;
    private User attacker;
    private User defender;
    public static Set<String> lines=new HashSet<>();
    static {
        lines.add("middle");
        lines.add("right");
        lines.add("left");
    }

    public int nextTurn() {
        turn--;
        if (attacker.equals(Guest)) {
            fight();
            check();
        }
        boolean flag = true;
        {
            boolean flag2 = true;
            for (String s : Host.getSide().getCastles().keySet()) {
                if (Host.getSide().getCastles().get(s).getHitpoint() > 0) {
                    flag2=false;
                }
            }
            if (flag2) {
                flag = false;
            }
            flag2=true;
            for (String s : Guest.getSide().getCastles().keySet()) {
                if (Guest.getSide().getCastles().get(s).getHitpoint() > 0) {
                    flag2=false;
                }
            }
            if (flag2) {
                flag = false;
            }
        }
        if ((turn > 0 && flag) || attacker.equals(Host)) {
            movesLeft = 3;
            attacksLeft = 1;
            if (attacker.equals(Host)) {
                attacker = Guest;
                defender = Host;
            } else {
                attacker = Host;
                defender = Guest;
            }
            return 0;
        } else {
            int goldForHost = 0;
            int xpForHost = 0;
            int xpForGuest = 0;
            int goldForGuest = 0;
            for (String s : Host.getSide().getCastles().keySet()) {
                xpForHost += Host.getSide().getCastles().get(s).getHitpoint();
                if (Host.getSide().getCastles().get(s).getHitpoint() <= 0) {
                    goldForGuest += 25;
                }
            }
            for (String s : Guest.getSide().getCastles().keySet()) {
                xpForGuest += Guest.getSide().getCastles().get(s).getHitpoint();
                if (Guest.getSide().getCastles().get(s).getHitpoint() <= 0) {
                    goldForHost += 25;
                }
            }
            Host.increaseGold(goldForHost);
            Guest.increaseGold(goldForGuest);
            Host.increaseXP(xpForHost);
            Guest.increaseXP(xpForGuest);
            if (xpForHost == xpForGuest) {
                return 3;
            }
            if (xpForHost > xpForGuest) {
                return 1;
            } else {
                return 2;
            }
        }
    }

    public void deployFireBall(String line) {
        defender.getSide().getCastles().get(line).hit(new GameFireBall(attacker, line, this));
        attacksLeft--;
        defender.getSide().getCastles().get(line).check();
    }

    public void deployHeal(String line, int row) {
        GameHeal toAdd = new GameHeal(attacker, line, row, this);
        attacker.getSide().getField().get(line)[row].add(toAdd);
        field.get(line)[row].add(toAdd);
        attacksLeft--;
    }

    public int deployTroop(String name, String line, int row) {
        if (attacker.equals(Host)) {
            if (row > 4) {
                return 1;
            }
        } else {
            if (row < 12) {
                return 1;
            }
        }
        if (attacksLeft == 0) {
            return 2;
        }
        Armament toAdd = new Armament(attacker, line, this, row, Card.CardMaker(name));
        attacker.getSide().getField().get(line)[row].add(toAdd);
        field.get(line)[row].add(toAdd);
        attacksLeft--;
        return 0;
    }

    public Battle(int turn, User host, User guest) {
        this.turn = turn * 2;
        Host = host;
        Guest = guest;
        attacker=host;
        defender=guest;
        host.setSide(new BattleSide(host, guest, this));
        guest.setSide(new BattleSide(guest,host,this));
    }

    public static int lineToInt(String line) {
        if (line.equals("left")) {
            return 0;
        }
        if (line.equals("middle")) {
            return 1;
        }
        if (line.equals("right")) {
            return 2;
        }
        return -1;
    }

    public void moveTroop(String line, int row, String direction) {
        for (int i = 0; i < attacker.getSide().getField().get(line)[row].size(); i++) {
            drawable current = attacker.getSide().getField().get(line)[row].get(i);
            if (current instanceof Armament) {
                ((Armament) current).move(direction);
                break;
            }
        }
    }

    public void fight() {
        for (String s : Guest.getSide().getField().keySet()) {
            for (int i = 0; i < Guest.getSide().getField().get(s).length; i++) {
                for (int j = 0; j < Guest.getSide().getField().get(s)[i].size(); j++) {
                    drawable current = Guest.getSide().getField().get(s)[i].get(j);
                        current.fight();
                }
            }
        }
        for (String s : Host.getSide().getField().keySet()) {
            for (int i = 0; i < Host.getSide().getField().get(s).length; i++) {
                for (int j = 0; j < Host.getSide().getField().get(s)[i].size(); j++) {
                    drawable current = Host.getSide().getField().get(s)[i].get(j);
                        current.fight();
                }
            }
        }
    }

    public void check() {
        for (String s : Guest.getSide().getField().keySet()) {
            for (int i = 0; i < Guest.getSide().getField().get(s).length; i++) {
                for (int i1 = Guest.getSide().getField().get(s)[i].size() - 1; i1 >= 0; i1--) {
                    drawable current = Guest.getSide().getField().get(s)[i].get(i1);
                    current.check();
                }
            }
        }
        for (String s : Host.getSide().getField().keySet()) {
            for (int i = 0; i < Host.getSide().getField().get(s).length; i++) {
                for (int j = Host.getSide().getField().get(s)[i].size() - 1; j >= 0; j--) {
                    drawable current = Host.getSide().getField().get(s)[i].get(j);
                    current.check();
                }
            }
        }
    }
    public User getHost() {
        return Host;
    }

    public User getGuest() {
        return Guest;
    }

    public User getAttacker() {
        return attacker;
    }

    public User getDefender() {
        return defender;
    }

    public HashMap<String, ArrayList<drawable>[]> getField() {
        return field;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public int getAttacksLeft() {
        return attacksLeft;
    }

    public void decreaseMoves() {
        movesLeft--;
    }
}
