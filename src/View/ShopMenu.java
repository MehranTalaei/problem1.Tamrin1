package View;

import Controler.ShopControl;
import Model.User;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ShopMenu {
    User CurrentUser;

    Pattern showMenu = Pattern.compile("show current menu");
    Pattern back = Pattern.compile("back");
    Pattern buyCard = Pattern.compile("buy card (?<name>.+)");
    Pattern sellCard = Pattern.compile("sell card (?<name>.+)");

    public void run(Scanner scanner) {
        String command = scanner.nextLine();
        if (command.matches(back.pattern())) {
            System.out.println("Entered main menu!");
            MainMenu mainMenu = new MainMenu(CurrentUser);
            mainMenu.run(scanner);
        } else if (command.matches(buyCard.pattern())) {
            String output = ShopControl.buyCard(buyCard.matcher(command), CurrentUser);
            System.out.println(output);
            this.run(scanner);
        } else if (command.matches(sellCard.pattern())) {
            String output = ShopControl.sellCard(sellCard.matcher(command), CurrentUser);
            System.out.println(output);
            this.run(scanner);
        } else if (command.matches(showMenu.pattern())) {
            System.out.println("Shop Menu");
            this.run(scanner);
        } else {
            System.out.println("Invalid command!");
            this.run(scanner);
        }
    }

    public ShopMenu(User currentUser) {
        CurrentUser = currentUser;
    }
}
