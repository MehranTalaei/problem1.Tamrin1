package Model.Drawables;

import Model.Battle;
import Model.User;

public class Castle extends drawable{
    String line;
    int maxHitPoint;
    public Castle(User owner, String line, Battle battle) {
        super(owner,line,battle);
        this.line = line;
        if (line.equals("left") || line.equals("right")) {
            this.hitpoint = 2500 * owner.getLevel();
        } else {
            this.hitpoint = 3600 * owner.getLevel();
        }
        this.damage = owner.getLevel() * 500;
        maxHitPoint=hitpoint;
        if (battle.getHost().equals(owner)) {
            row = 0;
        } else {
            row = 16;
        }
    }

    @Override
    public void fight() {
        if (battle.getHost().equals(owner)) {
            for (int i = 0; i < enemy.getSide().getField().get(line)[1].size(); i++) {
                drawable current = enemy.getSide().getField().get(line)[1].get(i);
                if (current instanceof Armament) {
                    current.hit(this);
                    this.hit(current);
                }
            }
        } else {
            for (int i = 0; i < enemy.getSide().getField().get(line)[15].size(); i++) {
                 drawable current = ( enemy.getSide().getField().get(line)[15].get(i));
                if (current instanceof Armament) {
                    current.hit(this);
                    this.hit(current);
                }
            }
        }
        check();
    }

    public int getMaxHitPoint() {
        return maxHitPoint;
    }
}
