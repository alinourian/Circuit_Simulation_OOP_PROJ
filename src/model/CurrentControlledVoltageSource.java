package model;

import enums.Type;

public class CurrentControlledVoltageSource extends VoltageSource {
    private final double gain;
    private final Branch branch;

    public CurrentControlledVoltageSource(String name, Node nodeIn, Node nodeOut,
                                          double gain, Branch branch) {
        super(name, nodeIn, nodeOut);
        this.gain = gain;
        this.branch = branch;
    }

    @Override
    public double getVoltage(Node node) {
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
        return getVoltage(node);
    }

    @Override
    public Type getType() {
        return Type.C_C_V_S;
    }

    @Override
    public String toString() {
        return  name + "       " +
                nodeP + "       " +
                nodeN + "       " +
                branch + "      " +
                gain;
    }
}
