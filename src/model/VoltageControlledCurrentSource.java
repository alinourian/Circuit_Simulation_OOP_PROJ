package model;

public class VoltageControlledCurrentSource extends CurrentSource {
    private final double gain;
    private final Node controllerNode1;
    private final Node controllerNode2;

    public VoltageControlledCurrentSource(String name, Node nodeIn, Node nodeOut,
                                          double gain, Node controllerNode1, Node controllerNode2) {
        super(name, nodeIn, nodeOut);
        this.gain = gain;
        this.controllerNode1 = controllerNode1;
        this.controllerNode2 = controllerNode2;
    }

    @Override
    public double getCurrent(Node node, double time) {
        double value = gain * (controllerNode1.getVoltage() - controllerNode2.getVoltage());
        if (node.getName().equals(nodeP.getName())) {
            return value;
        } else {
            return -1 * value;
        }
    }

    @Override
    public double getValue(Node node, double time) {
        return getCurrent(node, time);
    }

    @Override
    public String getType() {
        return "VoltageSourceControlledCurrentSource";
    }
}
