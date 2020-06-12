package view;

import java.util.Scanner;

public abstract class ConsoleScanner {
    private static final Scanner scanner = new Scanner(System.in);
    public static void run() {
        System.out.println("");
        while(true) {
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("end")) {
                break;
            } else {
                System.err.println("Command is not valid! please try again.");
            }
        }
    }
}
