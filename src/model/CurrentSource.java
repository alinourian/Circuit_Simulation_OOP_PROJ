package model;

public class CurrentSource extends Element {
    private double current;
    private double amplitude;
    private double frequency;
    private double phase;

    public CurrentSource(String name, Node nodeP, Node nodeN, double current,
                         double amplitude, double frequency, double phase) {
        super(name, nodeP, nodeN);
        this.current = current;
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.phase = phase;
    }

    @Override
    public String getType() {
        return "currentSource";
    }
}
