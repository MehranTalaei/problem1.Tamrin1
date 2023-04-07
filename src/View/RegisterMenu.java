package View;

import Controler.RegisterControl;
import Model.Game;
import Model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterMenu {

    Pattern registerCommand = Pattern.compile("register username (?<username>.+) password (?<password>.+)");
    Pattern loginCommand = Pattern.compile("login username (?<username>.+) password (?<password>.+)");
    Pattern exit = Pattern.compile("Exit");
    Pattern showMenu = Pattern.compile("show current menu");

    public void run(Scanner scanner) {
        String command = scanner.nextLine();
        if (command.matches(registerCommand.pattern())) {
            System.out.println(RegisterControl.register(registerCommand.matcher(command)));
            this.run(scanner);
        } else if (command.matches(loginCommand.pattern())) {
            String output = RegisterControl.login(loginCommand.matcher(command));
            System.out.println(output);
            if (output.endsWith("logged in!")) {
                Matcher mat = loginCommand.matcher(command);
                mat.find();
                String username = mat.group("username");
                MainMenu mainMenu = new MainMenu(Game.getUserByName(username));
                mainMenu.run(scanner);
            } else {
                this.run(scanner);
            }
        } else if (command.matches(exit.pattern())) {
            return;
        } else if (command.matches(showMenu.pattern())) {
            System.out.println("Register/Login Menu");
            this.run(scanner);
        } else {
            System.out.println("Invalid command!");
            this.run(scanner);
        }
    }
}
