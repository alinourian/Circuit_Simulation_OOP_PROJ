package model;

public class Resistor extends Element {
    private final double resistance;

    public Resistor(String name, Node nodeP, Node nodeN, double resistance) {
        super(name, nodeP, nodeN);
        this.resistance = resistance;
    }

    @Override
    public void updateElementCurrent() {
        helpCurrent = current;
        current = (nodeP.getVoltage() - nodeN.getVoltage()) / resistance;
    }

    @Override
    public void setBackElementCurrent() {
        current = helpCurrent;
    }

    @Override
    public double getValue() {
        return resistance;
    }

    @Override
    public String getType() {
        return "resistor";
    }
    
}
