package model;

public class Element extends Branch {
    protected double current;
    protected double helpCurrent;

    public Element(String name, Node nodeP, Node nodeN) {
        super(name, nodeP, nodeN);
        this.current = 0;
        this.helpCurrent = 0;
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
