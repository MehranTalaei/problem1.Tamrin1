import ViewAndControler.LoginMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LoginMenu loginMenu=new LoginMenu();
        Scanner scanner = new Scanner(System.in);
        loginMenu.run(scanner);
    }
}