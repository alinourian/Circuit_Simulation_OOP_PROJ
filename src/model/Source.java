package model;

import enums.Type;

import java.util.ArrayList;

public class Source extends Branch {
    protected ArrayList<Double> currents;

    public Source(String name, Node nodeP, Node nodeN) {
        super(name, nodeP, nodeN);
        currents = new ArrayList<>();
    }

    public double getVoltage(Node node) {
        return 0;
    }

    public double getCurrent(Node node) {
        return 0;
    }

    public String getName() {
        return name;
    }

    public double getValue(Node node) {
        return 0;
    }

    public Node getNodeP() {
        return nodeP;
    }

    public Node getNodeN() {
        return nodeN;
    }

    public Type getType() {
        return Type.SOURCE;
    }

    public ArrayList<Double> getCurrents() {
        return currents;
    }

    @Override
    public String toString() {
        return  name + "        " +
                nodeP + "       " +
                nodeN;
    }
}
