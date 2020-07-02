package main;

import controller.Solver;
import view.FileScanner;

public class Main {
    public static void main(String[] args) {
        if (FileScanner.run()) {
            Solver solver = new Solver();
            solver.run();
            //ShowCircuit.showInConsole();
            //write on file
            //run console
        }
    }
}
