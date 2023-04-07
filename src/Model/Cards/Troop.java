package Model.Cards;

import java.util.HashSet;
import java.util.Set;

abstract public class Troop extends Card{
    protected int hitpoint;
    public static Set<String> troopName=new HashSet<>();
    static {
        troopName.add("Ice Wizard");
        troopName.add("Barbarian");
        troopName.add("Baby Dragon");
    }
    public int getHitpoint() {
        return hitpoint;
    }
}
