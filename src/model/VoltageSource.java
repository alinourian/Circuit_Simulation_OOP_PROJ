package model;

public class VoltageSource extends Element {
    private double voltageDC;
    private boolean isDC;
    private double amplitudeVoltage;
    private double frequency;


    public VoltageSource(String name, Node nodeP, Node nodeN, double voltageDC) {
        super(name, nodeP, nodeN);
        this.isDC = true;
        this.voltageDC = voltageDC;
        this.amplitudeVoltage = 0;
        this.frequency = 0;
    }

    public VoltageSource(String name, Node nodeP, Node nodeN, double voltageDC, double amplitudeVoltage, double frequency) {
        super(name, nodeP, nodeN);
        this.isDC = false;
        this.voltageDC = voltageDC;
        this.amplitudeVoltage = amplitudeVoltage;
        this.frequency = frequency;
    }

}
