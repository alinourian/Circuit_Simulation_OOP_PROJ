package model;

public class CurrentControlledCurrentSource extends CurrentSource{

    public CurrentControlledCurrentSource(String name, Node nodeP, Node nodeN, double currentDC, double amplitude, double frequency, double phase) {
        super(name, nodeP, nodeN, currentDC, amplitude, frequency, phase);
    }
}

