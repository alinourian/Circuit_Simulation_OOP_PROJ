package main;

import com.sun.org.apache.xml.internal.serializer.ElemDesc;
import controller.InputController;
import controller.Solver;
import model.Capacitor;
import model.Element;
import model.Node;
import model.Resistor;
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
