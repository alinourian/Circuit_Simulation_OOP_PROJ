package model;

public class CurrentSource extends Source {
    private double currentDC;
    private double amplitude;
    private double frequency;
    private double phase;

    public CurrentSource(String name, Node nodeP, Node nodeN, double currentDC,
                         double amplitude, double frequency, double phase) {
        super(name, nodeP, nodeN);
        this.currentDC = currentDC;
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.phase = phase;
    }

    @Override
    public double getCurrent(Node node, double time) {
        if (node.getName().equals(nodeP.getName())) {
            return getValue(time);
        } else {
            return -1 * getValue(time);
        }
    }

    @Override
    public double getValue(double time) {
        return currentDC + amplitude * Math.sin(2 * Math.PI * frequency + phase);
    }

    @Override
    public String getType() {
        return "currentSource";
    }
}
