package Model.Drawables;
import Model.Battle;
import Model.Cards.Troop;
import Model.User;

abstract public class drawable {
    protected String name;
    protected User owner;
    protected User enemy;
    protected int row;
    public String line;
    protected int hitpoint;
    protected int damage;
    protected Battle battle;

    public drawable(User owner, String line, Battle battle) {
        this.battle=battle;
        this.owner = owner;
        this.line = line;
        this.battle=battle;
        if (battle.getHost().equals(owner)) {
            this.enemy = battle.getGuest();
        } else {
            this.enemy = battle.getHost();
        }
    }

    public void hit(drawable second) {
        this.hitpoint -= second.damage;
    }

    public void decreaseHP(int amount) {
        this.hitpoint -= amount;
    }

    public void check() {
        if (this.hitpoint <= 0) {
            owner.getSide().getField().get(line)[row].remove(this);
            battle.getField().get(line)[row].remove(this);
            this.hitpoint = 0;
        }
        if (this instanceof Armament) {
            if (this.hitpoint >= ((Troop) ((Armament) this).card).getHitpoint()) {
                this.hitpoint=((Troop) ((Armament) this).card).getHitpoint();
            }
        }
    }
    public abstract void fight();

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public int getRow() {
        return row;
    }

    public String getLine() {
        return line;
    }

    public int getHitpoint() {
        return hitpoint;
    }

    @Override
    public String toString() {
        return "drawable{" +
                "name='" + name + '\'' +
                ", row=" + row +
                ", line='" + line + '\'' +
                ", hitpoint=" + hitpoint +
                ", damage=" + damage +
                '}';
    }
}
