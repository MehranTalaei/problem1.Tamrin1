package Model.Drawables;

import Model.Battle;
import Model.Cards.Heal;
import Model.User;

public class GameHeal extends drawable{
    int turnsLeft = 2;
    public GameHeal(User owner, String line, int row, Battle battle) {
        super(owner, line, battle);
        this.row=row;
        damage = new Heal().getDamage();
        name = new Heal().getName();
    }

    @Override
    public void fight() {
        for (int i = 0; i < owner.getSide().getField().get(line)[row].size(); i++) {
            drawable current = owner.getSide().getField().get(line)[row].get(i);
            if(current instanceof Armament)
                current.hit(this);
        }
    }

    @Override
    public void check() {
        turnsLeft--;
        if (turnsLeft <= 0) {
            owner.getSide().getField().get(line)[row].remove(this);
            battle.getField().get(line)[row].remove(this);
        }
    }
}
