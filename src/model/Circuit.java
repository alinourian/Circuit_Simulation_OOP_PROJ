package model;

import controller.Solver;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Circuit {
    private ArrayList<Element> elements;
    private ArrayList<Node> nodes;
    private ArrayList<Union> unions;
    private double dv = 0;
    private double dt = 0;

    public Circuit(ArrayList<Element> elements, ArrayList<Node> nodes, double dv, double dt) {
        this.elements = elements;
        this.nodes = nodes;
        this.dv = dv;
        this.dt = dt;

    }
}
