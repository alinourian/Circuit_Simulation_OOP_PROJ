package model;

public class Resistor extends Element {
    private final double resistance;

    public Resistor(String name, Node nodeP, Node nodeN, double resistance) {
        super(name, nodeP, nodeN);
        this.resistance = resistance;
    }

    @Override
    public String getType() {
        return "resistor";
    }

    public double getResistance() {
        return resistance;
    }

    
}
