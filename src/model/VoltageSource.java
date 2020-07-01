package model;

public class VoltageSource extends Source {
    private double voltageDC;
    private double amplitude;
    private double frequency;
    private double phase;

    public VoltageSource(String name, Node nodeP, Node nodeN, double voltageDC,
                         double amplitude, double frequency, double phase) {
        super(name, nodeP, nodeN);
        this.voltageDC = voltageDC;
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.phase = phase;
    }

    public VoltageSource(String name, Node nodeP, Node nodeN) {
        super(name, nodeP, nodeN);
    }

    @Override
    public double getVoltage(Node node, double time) {
        double value = voltageDC + amplitude * Math.sin(2 * Math.PI * frequency * time + phase);
        if (node.getName().equals(nodeP.getName())) {
            return value;
        } else {
            return -1 * value;
        }
    }

    @Override
    public double getValue(Node node, double time) {
        return getVoltage(node, time);
    }

    @Override
    public String getType() {
        return "voltageSource";
    }

}
