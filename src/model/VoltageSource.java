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

    @Override
    public double getValue(double time) {
        return voltageDC + amplitude * Math.sin(2 * Math.PI * frequency + phase);
    }

    @Override
    public String getType() {
        return "voltageSource";
    }

}
