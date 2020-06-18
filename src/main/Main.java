package main;

import controller.InputController;
import controller.Solver;
import model.Node;
import view.FileScanner;

public class Main {
    public static void main(String[] args) {
        if (FileScanner.run()) {
            Solver solver = new Solver();
            solver.run();
            //write on file
            //run console
        }
    }
}
