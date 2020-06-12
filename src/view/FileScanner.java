package view;

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
                counter++;
            } while (scanner.hasNextLine());
        } catch (FileNotFoundException e) {
            System.err.println(e);
            return false;
        }
        System.out.println("File successfully uploaded.");
        return true;
    }
}
