package model;

import enums.Type;

public class Source extends Branch {

    public Source(String name, Node nodeP, Node nodeN) {
        super(name, nodeP, nodeN);
        setHeight(1);
        setWidth(1);
        setTheTypeSeries();
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

    @Override
    public String toString() {
        return  name + "        " +
                nodeP + "       " +
                nodeN;
    }
}
