package Model.Drawables;

import Model.Battle;
import Model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class BattleSide {
    private String side;
    private Battle battle;
    private User owner;
    private User enemy;
    private HashMap <String,Castle> castles=new HashMap<>();
    private HashMap<String,ArrayList<drawable> []>field=new HashMap<>();
    {
        field.put("middle", new ArrayList[17]);
        field.put("right", new ArrayList[17]);
        field.put("left", new ArrayList[17]);
        for (String s : field.keySet()) {
            for (int i = 0; i < field.get(s).length; i++) {
                field.get(s)[i] = new ArrayList<>();
            }
        }
    }

    public BattleSide(User owner, User enemy, Battle battle) {
        this.battle=battle;
        castles.put("middle", new Castle(owner, "middle",battle));
        castles.put("right", new Castle(owner, "right",battle));
        castles.put("left", new Castle(owner, "left",battle));
        if (battle.getHost().equals(owner)) {
            side = "host";
        } else {
            side = "guest";
        }
        this.owner = owner;
        this.enemy = enemy;
        if (side.equals("guest")) {
            field.get("middle")[16].add(castles.get("middle"));
            field.get("right")[16].add(castles.get("right"));
            field.get("left")[16].add(castles.get("left"));
        } else {
            field.get("middle")[0].add(castles.get("middle"));
            field.get("right")[0].add(castles.get("right"));
            field.get("left")[0].add(castles.get("left"));
        }
    }

    public HashMap<String, ArrayList<drawable>[]> getField() {
        return field;
    }

    public Battle getBattle() {
        return battle;
    }

    public User getEnemy() {
        return enemy;
    }

    public HashMap<String, Castle> getCastles() {
        return castles;
    }

}
