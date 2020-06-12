package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class FileScanner {
    private static final File file = new File("file.txt");

    public static void run() {
        try {
            Scanner scanner = new Scanner(file);
            int counter = 1;
            do {
                String line = scanner.nextLine();
                FileInputProcessor.inputProcess(line, counter);
                counter++;
            } while (scanner.hasNextLine());
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }
    }
}
