package Model.Drawables;

import Model.Battle;
import Model.User;

public class GameFireBall extends drawable {


    public GameFireBall(User owner, String line, Battle battle) {
        super(owner, line, battle);
        damage=1600;
    }

    @Override
    public void fight() {

    }

}
