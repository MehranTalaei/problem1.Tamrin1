package View;

import Controler.GameControl;
import Model.Battle;
import Model.User;

import java.util.Scanner;
import java.util.regex.Pattern;

public class GameMenu {
    User CurrentUser;
    Battle currentBattle;
    GameControl controller;

    Pattern showHitPoint = Pattern.compile("show the hitpoints left of my opponent");
    Pattern lineInfo = Pattern.compile("show line info (?<line>.+)");
    Pattern numberOfPlays = Pattern.compile("number of cards to play");
    Pattern numberOfMoves = Pattern.compile("number of moves left");
    Pattern moveTroop = Pattern.compile("move troop in line (?<line>.+) and row (?<row>-?\\d+) (?<direction>.+)");
    Pattern deployTroop = Pattern.compile("deploy troop (?<name>.+) in line (?<line>.+) and row (?<row>-?\\d+)");
    Pattern deployHeal = Pattern.compile("deploy spell Heal in line (?<line>.+) and row (?<row>-?\\d+)");
    Pattern deployFireBall = Pattern.compile("deploy spell Fireball in line (?<line>.+)");
    Pattern nextTurn = Pattern.compile("next turn");
    Pattern showMenu = Pattern.compile("show current menu");
    public void run(Scanner scanner) {
        String command = scanner.nextLine();
        if (command.matches(showHitPoint.pattern())) {
            String output = controller.showHitPoint();
            System.out.print(output);
            this.run(scanner);
        } else if (command.matches(lineInfo.pattern())) {
            String output = controller.showTroops(lineInfo.matcher(command));
            System.out.print(output);
            this.run(scanner);
        } else if (command.matches(numberOfPlays.pattern())) {
            String output = controller.numberOfCards();
            System.out.println(output);
            this.run(scanner);
        } else if (command.matches(numberOfMoves.pattern())) {
            String output = controller.numberOfMoves();
            System.out.println(output);
            this.run(scanner);
        } else if (command.matches(moveTroop.pattern())) {
            String output = controller.moveTroop(moveTroop.matcher(command));
            System.out.println(output);
            this.run(scanner);
        } else if (command.matches(deployTroop.pattern())) {
            String output = controller.deployTroop(deployTroop.matcher(command));
            System.out.println(output);
            this.run(scanner);
        } else if (command.matches(deployHeal.pattern())) {
            String output = controller.deployHeal(deployHeal.matcher(command));
            System.out.println(output);
            this.run(scanner);
        } else if (command.matches(deployFireBall.pattern())) {
            String output = controller.deployFireBall(deployFireBall.matcher(command));
            System.out.println(output);
            this.run(scanner);
        } else if (command.matches(nextTurn.pattern())) {
            String output = controller.nextTurn();
            System.out.println(output);
            if (output.startsWith("Game has ended")) {
                MainMenu mainMenu = new MainMenu(CurrentUser);
                mainMenu.run(scanner);
            } else {
                this.run(scanner);
            }
        } else if (command.matches(showMenu.pattern())) {
            System.out.println("Game Menu");
            this.run(scanner);
        } else {
            System.out.println("Invalid command!");
            this.run(scanner);
        }
    }

    public GameMenu(User currentUser, Battle currentBattle) {
        CurrentUser = currentUser;
        this.currentBattle = currentBattle;
        this.controller = new GameControl(currentBattle);
    }

}
