package Controler;

import Model.Battle;
import Model.Cards.*;
import Model.Drawables.Armament;
import Model.Drawables.drawable;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameControl {
    Battle battle;
    public String showHitPoint() {
        String s = new String();
        if (battle.getDefender().getSide().getCastles().get("middle").getHitpoint() == 0) {
            s += ("middle castle: " + -1 + "\n");
        } else {
            s += ("middle castle: " + battle.getDefender().getSide().getCastles().get("middle").getHitpoint() + "\n");
        }

        if (battle.getDefender().getSide().getCastles().get("left").getHitpoint() == 0) {
            s += ("left castle: " + -1 + "\n");
        } else {
            s += ("left castle: " + battle.getDefender().getSide().getCastles().get("left").getHitpoint() + "\n");
        }

        if (battle.getDefender().getSide().getCastles().get("right").getHitpoint() == 0) {
            s += ("right castle: " + -1 + "\n");
        } else {
            s += ("right castle: " + battle.getDefender().getSide().getCastles().get("right").getHitpoint() + "\n");
        }

        return s;
    }

    public String showTroops(Matcher matcher) {
        String s = new String();
        matcher.find();
        String line = matcher.group("line");
        if (!Battle.lines.contains(line)) {
            return "Incorrect line direction!\n";
        }
        s += (line + " line:" + "\n");

        for (int i = 1; i < battle.getField().get(line).length - 1; i++) {
            ArrayList<drawable> cell = battle.getField().get(line)[i];
            if (cell.size() > 0) {
                for (int j = 0; j < cell.size(); j++) {
                    s += ("row " + i + ":");
                    drawable current = cell.get(j);
                    s += (" " + current.getName() + ": " + current.getOwner().getUserName());
                    s += "\n";
                }
            }
        }
        return s;
    }

    public String numberOfCards() {
        String s = new String();
        s += "You can play " + battle.getAttacksLeft() + " cards more!";
        return s;
    }

    public String numberOfMoves() {
        String s = new String();
        s += "You have " + battle.getMovesLeft() + " moves left!";
        return s;
    }

    public String moveTroop(Matcher matcher) {
        matcher.find();
        String line = matcher.group("line");
        int row =Integer.parseInt(matcher.group("row")) ;
        String direction = matcher.group("direction");
        if (!Battle.lines.contains(line)) {
            return "Incorrect line direction!";
        } else if (row > 15 || row < 1) {
            return "Invalid row number!";
        } else if (!direction.equals("upward") && !direction.equals("downward")) {
            return "you can only move troops upward or downward!";
        } else if (battle.getMovesLeft() == 0) {
            return "You are out of moves!";
        }
        boolean flag = true;
        Armament toBeMoved = new Armament(battle.getAttacker(), line, battle, row, new BabyDragon());
        for (int i = 0; i < battle.getAttacker().getSide().getField().get(line)[row].size(); i++) {
            drawable current = battle.getAttacker().getSide().getField().get(line)[row].get(i);
            if (current instanceof Armament) {
                flag=false;
                toBeMoved = ((Armament) current);
                break;
            }
        }
        if (flag) {
            return "You don't have any troops in this place!";
        }
        if (toBeMoved.move(direction)) {
            return toBeMoved.getName() + " moved successfully to row " + toBeMoved.getRow() + " in line " + line;

        } else {
            return "Invalid move!";
        }
    }

    public String deployTroop(Matcher matcher) {
        matcher.find();
        String troopName = matcher.group("name");
        String line = matcher.group("line");
        int row = Integer.parseInt(matcher.group("row"));
        if (!Troop.troopName.contains(troopName)) {
            return "Invalid troop name!";
        } else if (!battle.getAttacker().getBattleDeck().contains(Card.CardMaker(troopName))) {
            return "You don't have " + troopName + " card in your battle deck!";
        } else if (!Battle.lines.contains(line)) {
            return "Incorrect line direction!";
        } else if (row > 15 || row < 1) {
            return "Invalid row number!";
        }
        int a = battle.deployTroop(troopName, line, row);
        if (a == 1) {
            return "Deploy your troops near your castles!";
        }
        if (a == 2) {
            return "You have deployed a troop or spell this turn!";
        }
        if (a == 0) {
            return "You have deployed " + troopName + " successfully!";
        }
        return null;
    }

    public String deployHeal(Matcher matcher) {
        matcher.find();
        String line = matcher.group("line");
        int row = Integer.parseInt(matcher.group("row"));
        if (!Battle.lines.contains(line)) {
            return "Incorrect line direction!";
        } else if (!battle.getAttacker().getBattleDeck().contains(new Heal())) {
            return "You don't have Heal card in your battle deck!";
        } else if (row > 15 || row < 1) {
            return "Invalid row number!";
        } else if (battle.getAttacksLeft() == 0) {
            return "You have deployed a troop or spell this turn!";
        } else {
            battle.deployHeal(line, row);
            return "You have deployed Heal successfully!";
        }
    }

    public String deployFireBall(Matcher matcher) {
        matcher.find();
        String line = matcher.group("line");
        if (!Battle.lines.contains(line)) {
            return "Incorrect line direction!";
        } else if (!battle.getAttacker().getBattleDeck().contains(new FireBall())) {
            return "You don't have Fireball card in your battle deck!";
        } else if (battle.getAttacksLeft() == 0) {
            return "You have deployed a troop or spell this turn!";
        } else if (battle.getDefender().getSide().getCastles().get(line).getHitpoint() <= 0) {
            return "This castle is already destroyed!";
        } else {
            battle.deployFireBall(line);
            return "You have deployed Fireball successfully!";
        }
    }

    public String nextTurn() {
        int a = battle.nextTurn();
        if (a == 0) {
            return "Player " + battle.getAttacker().getUserName() + " is now playing!";
        } else if (a == 1) {
            return "Game has ended. Winner: " + battle.getHost().getUserName();
        } else if (a == 2) {
            return "Game has ended. Winner: " + battle.getGuest().getUserName();
        } else {
            return "Game has ended. Result: Tie";
        }
    }

    public GameControl(Battle battle) {
        this.battle = battle;
    }
}
