package model;

public class Resistor extends Element {
    private final double resistance;

    public Resistor(String name, Node nodeP, Node nodeN, double resistance) {
        super(name, nodeP, nodeN);
        this.resistance = resistance;
    }

    public double getResistance() {
        return resistance;
    }

    
}
