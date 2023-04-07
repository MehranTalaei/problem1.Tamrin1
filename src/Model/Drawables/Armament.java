package Model.Drawables;

import Model.Battle;
import Model.Cards.*;
import Model.User;

public class Armament extends drawable{
    Card card;

    public Armament(User owner, String line, Battle battle, int row, Card card) {
        super(owner, line, battle);
        this.card = card;
        this.row = row;
        this.damage = card.getDamage();
        if (card instanceof Troop) {
            this.hitpoint = ((Troop) card).getHitpoint();
        }
        name = card.getName();
    }

    public boolean move(String direction) {
        int buffRow=row;
        if (direction.equals("upward")) {
            if (owner.equals(battle.getGuest())) {
                buffRow++;
            } else {
                buffRow++;
            }
        }
        if (direction.equals("downward")) {
            if (owner.equals(battle.getGuest())) {
                buffRow--;
            } else {
                buffRow--;
            }
        }
        boolean ans = (buffRow < 16 && buffRow > 0);
        if (ans) {
            battle.decreaseMoves();
            owner.getSide().getField().get(line)[row].remove(this);
            battle.getField().get(line)[row].remove(this);
            battle.getField().get(line)[buffRow].add(this);
            owner.getSide().getField().get(line)[buffRow].add(this);
            row = buffRow;
        }
        return  ans;
    }

    @Override
    public void fight() {

        for (int i = 0; i < enemy.getSide().getField().get(line)[row].size(); i++) {
            drawable current = enemy.getSide().getField().get(line)[row].get(i);
            if (current instanceof Armament) {
                if (card instanceof Barbarian && ((Armament) current).card instanceof BabyDragon) {

                } else {
                    if (current.damage < this.damage) {
                        current.decreaseHP(this.damage - current.damage);
                    }
                }
            }
        }
    }

    public Card getCard() {
        return card;
    }


}
