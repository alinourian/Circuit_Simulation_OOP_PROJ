package main;

import controller.InputController;
import controller.Solver;
import controller.UnionCreator;
import model.*;
import view.FileScanner;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        if (FileScanner.run()) {

            UnionCreator unionCreator = new UnionCreator();

            unionCreator.run();
            //if( unionCreator.run() )
            //{
                //Solver solver = new Solver();
                //solver.run();
            //}



            //write on file
            //run console

        }
    }
}
