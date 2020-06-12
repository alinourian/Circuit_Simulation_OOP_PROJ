package model;

public class Element {
    protected final String name;
    protected Node nodeP;
    protected Node nodeN;
    protected double voltage;
    protected double current;

    public Element(String name, Node nodeIn, Node nodeOut) {
        this.name = name;
        this.nodeP = nodeIn;
        this.nodeN = nodeOut;
    }

    public String getName() {
        return name;
    }

    public double getVoltage(double time) {
        return 0;
    }

    public double getCurrent() {
        return 0;
    }

    public Node getNodeP() {
        return nodeP;
    }

    public Node getNodeN() {
        return nodeN;
    }

    public String getType() {
        return "Element";
    }
}
