package main;

import controller.Controller;
import view.ConsoleScanner;
import view.FileScanner;

public class Main {
    public static void main(String[] args) {
        if (FileScanner.run()) {
            ConsoleScanner.run();
        }
    }
}
