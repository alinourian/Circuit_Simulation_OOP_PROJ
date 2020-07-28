package view.file;

import controller.InputController;
import controller.Solver;
import view.Errors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public abstract class FileScanner {
    public static boolean hasFile = false;

    public static boolean runProgram(File file) {
        InputController.getInstance().restartProgram();
        if (run(file)) {
            hasFile = true;
            //  Solve
            Solver solver = new Solver();
            return solver.run();

            //  Console scanning & Read file
            //ConsoleScanner.run();

        }
        return false;
    }

    public static boolean run(File file) {
        try {
            Scanner scanner = new Scanner(file);
            int counter = 1;
            do {
                String line = scanner.nextLine();
                if (!FileInputProcessor.inputProcess(line, counter)) {
                    return false;
                }
                if (scanner.hasNextLine() && line.startsWith(".tran")) {
                    Errors.tranError1(counter);
                    return false;
                } else if (!scanner.hasNextLine() && !line.startsWith(".tran")) {
                    Errors.tranError2(counter);
                    return false;
                }
                counter++;
            } while (scanner.hasNextLine());
            scanner.close();
        } catch (FileNotFoundException | RuntimeException e) {
            Errors.exceptionsError(e);
            return false;
        }
        if (InputController.getInstance().getDeltaV() <= 0 ||
                InputController.getInstance().getDeltaI() <= 0 || InputController.getInstance().getDeltaT() <= 0) {
            Errors.constantsError();
            return false;
        }


        Errors.fileUpload();
        return true;
    }
}
