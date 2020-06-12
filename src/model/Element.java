package model;

public class Element {
    protected String name;
    protected Node nodeP;
    protected Node nodeN;
    protected double voltage;
    protected double current;

    public Element(String name, Node nodeIn, Node nodeOut) {
        this.name = name;
        this.nodeP = nodeIn;
        this.nodeN = nodeOut;
    }

    public double getVoltage(double time) {
        return 0;
    }

    public double getCurrent() {
        return 0;
    }
}
