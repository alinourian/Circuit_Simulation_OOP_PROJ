package model;

import enums.Type;

public class CurrentControlledCurrentSource extends CurrentSource {
    private final double gain;
    private final Branch branch;

    public CurrentControlledCurrentSource(String name, Node nodeIn, Node nodeOut,
                                          double gain, Branch branch) {
        super(name, nodeIn, nodeOut);
        this.gain = gain;
        this.branch = branch;
    }

    @Override
    public double getCurrent(Node node) {
        double value;
        if (branch instanceof Element) {
            Element element = (Element)branch;
            value = gain * element.getCurrent(element.getNodeN());
        } else {
            Source source = (Source)branch;
            value = gain * source.getCurrent(source.getNodeP());
        }
        if (node.getName().equals(nodeP.getName())) {
            return value;
        } else {
            return -1 * value;
        }
    }

    @Override
    public double getValue(Node node) {
        return getCurrent(node);
    }

    @Override
    public Type getType() {
        return Type.C_C_C_S;
    }

    @Override
    public String toString() {
        return  name + "       " +
                nodeP + "       " +
                nodeN + "       " +
                branch.getName() + "      " +
                gain;
    }
}