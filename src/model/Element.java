package model;

public class Element {
    protected final String name;
    protected Node nodeP;
    protected Node nodeN;
    protected double current;
    protected double helpCurrent;

    public Element(String name, Node nodeIn, Node nodeOut) {
        this.name = name;
        this.nodeP = nodeIn;
        this.nodeN = nodeOut;
        this.current = 0;
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

    public double getValue() {
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
