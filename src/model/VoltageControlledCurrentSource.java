package model;

public class VoltageControlledCurrentSource extends CurrentSource{
    public VoltageControlledCurrentSource(String name, Node nodeP, Node nodeN, double currentDC, double amplitude, double frequency, double phase) {
        super(name, nodeP, nodeN, currentDC, amplitude, frequency, phase);
    }
}
