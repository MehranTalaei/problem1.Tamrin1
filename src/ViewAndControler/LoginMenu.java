package ViewAndControler;

import Model.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu {
    static Pattern loginCommand = Pattern.compile("\\s*login\\s+i\\s+(?<id>\\S+)\\s+p\\s+(?<password>\\S+)\\s*");
    static Pattern registerCommand = Pattern.compile("\\s*register\\s+i\\s+(?<id>\\S+)\\s+u\\s+(?<username>\\S+)\\s+p\\s+(?<password>\\S+)\\s*");
    static Pattern exit = Pattern.compile("\\s*exit\\s*");
    public void run() {
/*
        Commands login = new Commands("login\\s+i\\s+(?<id>\\S+)\\s+p\\s+(?<password>\\w+)");
        Matcher hell = login.getMatcher("login i 234 p afkja");
        hell.find();
        System.out.println(hell.matches());
        System.out.println(hell.group("password"));
*/
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        if (command.matches(exit.pattern())) {
            return;
        } else if (command.matches(registerCommand.pattern())) {
            System.out.println(register(registerCommand.matcher(command)));
            this.run();
        } else if (command.matches(loginCommand.pattern())) {
            String output = login(loginCommand.matcher(command));
            System.out.println(output);
            if (output.equals("User successfully logged in!")) {
                MessageMenu messageMenu = new MessageMenu();
                Matcher mat=loginCommand.matcher(command);
                mat.find();
                String id = mat.group("id");
                User current = Messenger.getUserById(id);
                messageMenu.setCurrentUser(current);
                messageMenu.run();
            } else {
                this.run();
            }
        } else {
/*
            Chat chat=new Group(new User("saa","sadf","sfgg"),"dd","adf");
            System.out.println(chat.getClass().getSimpleName().equalsIgnoreCase("group"));
*/
            System.out.println("Invalid command!");
            this.run();
        }
        return;
    }

    private String login(Matcher matcher) {
        matcher.find();
        String password = matcher.group("password");
        String id = matcher.group("id");
        if (Messenger.getUserById(id) == null) {
            return "No user with this id exists!";
        } else if (!Messenger.getUserById(id).getPassword().equals(password)) {
            return "Incorrect password!";
        } else {
            return "User successfully logged in!";
        }
    }

    private String register(Matcher matcher) {
        matcher.find();
        String password = matcher.group("password");
        String id = matcher.group("id");
        String username = matcher.group("username");
        if (!username.matches("\\w+")) {
            return "Username's format is invalid!";
        } else if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\*\\.\\!\\@\\$\\%\\^\\&\\(\\)\\{\\}\\[\\]\\:\\;\\<\\>\\,\\?\\/\\~\\_\\+-\\=\\|]).{8,32}$")) {
            return "Password is weak!";
        } else if (Messenger.getUserById(id) != null) {
            return "A user with this ID already exists!";
        } else {
            User newUser = new User(id, username, password);
            Messenger.addUser(newUser);
            return "User has been created successfully!";
        }
    }

}
