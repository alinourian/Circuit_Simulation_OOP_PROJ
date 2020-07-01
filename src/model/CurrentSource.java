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

    public CurrentSource(String name, Node nodeIn, Node nodeOut) {
        super(name, nodeIn, nodeOut);
    }

    @Override
    public double getCurrent(Node node, double time) {
        double value = currentDC + amplitude * Math.sin(2 * Math.PI * frequency * time + phase);
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
        return "currentSource";
    }
}
