package View;

import Controler.MainControl;
import Controler.RegisterControl;
import Model.Battle;
import Model.Drawables.Armament;
import Model.Drawables.Castle;
import Model.Drawables.drawable;
import Model.Game;
import Model.User;

import javax.security.auth.SubjectDomainCombiner;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {
    User Currentuser;

    Pattern showMenu = Pattern.compile("show current menu");
    Pattern listOfUsersCommand = Pattern.compile("list of users");
    Pattern scoreBoardCommand = Pattern.compile("scoreboard");
    Pattern logout = Pattern.compile("logout");
    Pattern profileCommand = Pattern.compile("profile menu");
    Pattern shopMenuCommand = Pattern.compile("shop menu");
    Pattern startGameCommand = Pattern.compile("start game turns count (?<turnCount>-?\\d+) username (?<username>.+)");
    public void run(Scanner scanner) {
        String command = scanner.nextLine();
        if (command.matches(listOfUsersCommand.pattern())) {
            String output = MainControl.ShowListOfUsers();
            System.out.print(output);
            this.run(scanner);
        } else if (command.matches(scoreBoardCommand.pattern())) {
            String output=MainControl.ShowScoreBoard();
            System.out.print(output);
            this.run(scanner);
        } else if (command.matches(logout.pattern())) {
            System.out.println("User "+Currentuser.getUserName()+" logged out successfully!");
            RegisterMenu registerMenu = new RegisterMenu();
            registerMenu.run(scanner);
        } else if (command.matches(profileCommand.pattern())) {
            System.out.println("Entered profile menu!");
            ProfileMenu profileMenu=new ProfileMenu(Currentuser);
            profileMenu.run(scanner);
        } else if (command.matches(shopMenuCommand.pattern())) {
            System.out.println("Entered shop menu!");
            ShopMenu shopMenu = new ShopMenu(Currentuser);
            shopMenu.run(scanner);
        } else if (command.matches(startGameCommand.pattern())) {
            String output = MainControl.startNewGame(startGameCommand.matcher(command));
            System.out.println(output);
            if (output.startsWith("Battle started with user ")) {
                Matcher mat = startGameCommand.matcher(command);
                mat.find();
                String guestName = mat.group("username");
                int turnCount = Integer.parseInt(mat.group("turnCount"));
                Battle battle = new Battle(turnCount, Currentuser, Game.getUserByName(guestName));
                GameMenu gameMenu = new GameMenu(Currentuser, battle);
                gameMenu.run(scanner);
            } else {
                this.run(scanner);
            }
        } else if (command.matches(showMenu.pattern())) {
            System.out.println("Main Menu");
            this.run(scanner);
        } else {
            System.out.println("Invalid command!");
            this.run(scanner);
        }
    }

    public MainMenu(User currentuser) {
        Currentuser = currentuser;
    }
}
