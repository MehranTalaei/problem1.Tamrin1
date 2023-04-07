package View;

import Controler.ProfileControl;
import Model.User;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ProfileMenu {
    User CurrentUser;

    Pattern showMenu = Pattern.compile("show current menu");
    Pattern back = Pattern.compile("back");
    Pattern changePasswordCommand = Pattern.compile("change password old password (?<oldPassword>.+) new password (?<newPassword>.+)");
    Pattern infoCommand = Pattern.compile("Info");
    Pattern removeCard = Pattern.compile("remove from battle deck (?<name>.+)");
    Pattern addCard = Pattern.compile("add to battle deck (?<name>.+)");
    Pattern showDeck = Pattern.compile("show battle deck");

    public void run(Scanner scanner) {
        String command=scanner.nextLine();
        if (command.matches(back.pattern())) {
            System.out.println("Entered main menu!");
            MainMenu mainMenu = new MainMenu(CurrentUser);
            mainMenu.run(scanner);
        } else if (command.matches(changePasswordCommand.pattern())) {
            String output = ProfileControl.changePassword(changePasswordCommand.matcher(command), CurrentUser);
            System.out.println(output);
            this.run(scanner);
        } else if (command.matches(infoCommand.pattern())) {
            String output = ProfileControl.Info(CurrentUser);
            System.out.print(output);
            this.run(scanner);
        } else if (command.matches(removeCard.pattern())) {
            String output = ProfileControl.removeCard(removeCard.matcher(command), CurrentUser);
            System.out.println(output);
            this.run(scanner);
        } else if (command.matches(addCard.pattern())) {
            String output = ProfileControl.addToDeck(addCard.matcher(command), CurrentUser);
            System.out.println(output);
            this.run(scanner);
        } else if (command.matches(showDeck.pattern())) {
            String output = ProfileControl.showDeck(CurrentUser);
            System.out.print(output);
            this.run(scanner);
        } else if (command.matches(showMenu.pattern())) {
            System.out.println("Profile Menu");
            this.run(scanner);
        } else {
            System.out.println("Invalid command!");
            this.run(scanner);
        }
    }

    public ProfileMenu(User currentUser) {
        CurrentUser = currentUser;
    }
}
