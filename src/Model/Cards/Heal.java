package Model.Cards;

public class Heal extends Spell{
    int turnsLeft;
    public Heal() {
        name = "Heal";
        priceBuy = 150;
        priceSell = 120;
        damage = -1000;
        turnsLeft = 2;
    }
}
