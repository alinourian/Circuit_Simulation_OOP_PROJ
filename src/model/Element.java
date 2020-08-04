package model;

import enums.Type;

import java.util.ArrayList;

public class Element extends Branch {
    protected double current;
    protected double helpCurrent;
    protected ArrayList<Double> currents;

    public Element(String name, Node nodeP, Node nodeN) {
        super(name, nodeP, nodeN);
        this.current = 0;
        this.helpCurrent = 0;
        this.currents = new ArrayList<>();
        setHeight(1);
        setWidth(1);
        setTheTypeSeries();
    }

    public void updateElementCurrent() {
    }

    public void setBackElementCurrent() {

    }

    public String getName() {
        return name;
    }

    public double getCurrent(Node node) {
        if (node.getName().equals(nodeP.getName())) {
            return -1 * current;
        } else {
            return current;
        }
    }

    public double getVoltage() {
        return nodeP.getVoltage() - nodeN.getVoltage();
    }

    public ArrayList<Double> getCurrents() {
        return currents;
    }

    public double getValue() {
        return 0;
    }

    public Node getNodeP() {
        return nodeP;
    }

    public Node getNodeN() {
        return nodeN;
    }

    public Type getType() {
        return Type.ELEMENT;
    }

    @Override
    public String toString() {
        return  name + "       " +
                nodeP + "       " +
                nodeN;
    }
}
