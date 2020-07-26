package view.console;

import controller.InputController;
import model.Node;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ConsoleScanner {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InputController controller = InputController.getInstance();

    public static void run() {
        while(true) {
            String command = scanner.nextLine();
            Pattern pattern = Pattern.compile("(\\d+)\\s+(\\d+)\\s+(\\S+)");
            Matcher matcher = pattern.matcher(command);
            if (matcher.matches()) {
                double value = controller.getValueOfString(matcher.group(3));
                Node node1 = controller.findNode(matcher.group(1));
                Node node2 = controller.findNode(matcher.group(2));
                if (value == -1 || !controller.getNodes().contains(node1) || !controller.getNodes().contains(node2)) {
                    System.err.println("ERROR - Command is not valid! please try again.");
                } else {
                    int step = (int) Math.round(value / InputController.getInstance().getDeltaT());
                    double voltage = node1.getVoltages().get(step) - node2.getVoltages().get(step);
                    System.out.println("Voltage equals to : " + voltage);
                }
            } else if (command.equalsIgnoreCase("end")) {
                break;
            } else {
                System.err.println("ERROR - Command is not valid! please try again.");
            }
        }
    }
}
