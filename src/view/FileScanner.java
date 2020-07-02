package view;

import controller.InputController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class FileScanner {
    private static final File file = new File("file.txt");

    public static boolean run() {
        try {
            Scanner scanner = new Scanner(file);
            int counter = 1;
            do {
                String line = scanner.nextLine();
                if (!FileInputProcessor.inputProcess(line, counter)) {
                    return false;
                }
                if (scanner.hasNextLine() && line.startsWith(".tran")) {
                    System.err.println("error :");
                    System.err.println("Line " + counter + " : <.tran> is in wrong line");
                    return false;
                } else if (!scanner.hasNextLine() && !line.startsWith(".tran")) {
                    System.err.println("error :");
                    System.err.println("Line " + counter + " : <.tran> not found!");
                    return false;
                }
                counter++;
            } while (scanner.hasNextLine());
        } catch (FileNotFoundException e) {
            System.err.println(e);
            return false;
        }
        if (InputController.getInstance().getDeltaV() <= 0 ||
                InputController.getInstance().getDeltaI() <= 0 || InputController.getInstance().getDeltaT() <= 0) {
            System.err.println("error -1 :");
            System.err.println("<dV, dI, dT> not initialised correctly!");
            return false;
        }

        // TODO making unions


        System.out.println("File successfully uploaded.");
        return true;
    }
}
