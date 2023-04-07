import Model.Game;
import Model.User;

public class test {
    public static void main(String[] args) {
        Game.addUser(new User("1", "halle"));
        Game.addUser(new User("2", "pass2"));
        Game.addUser(new User("3", "pass3"));
        Game.addUser(new User("4", "pass4"));
        Game.addUser(new User("5", "pass5"));
        Game.addUser(new User("6", "pass6"));
        Game.addUser(new User("7", "pass7"));
        Game.getUserByName("6").increaseXP(123);
        Game.getUserByName("1").increaseXP(123);
        Game.getUserByName("2").increaseXP(124);
        for (User user : Game.ScoreBoard()) {
            System.out.println(user.getUserName() + "    " + user.getLevel() + "   " + user.getXp());
        }
    }
}
