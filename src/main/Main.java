package main;

import controller.Controller;
import view.ConsoleScanner;
import view.FileScanner;

public class Main {
    public static void main(String[] args) {
        FileScanner.run();
        ConsoleScanner.run();
    }
}
