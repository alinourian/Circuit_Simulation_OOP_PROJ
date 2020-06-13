package model;

import controller.InputController;

public class Capacitor extends Element {
    private double capacity;
    private double V_p;
    private double V_n;

    public Capacitor(String name, Node nodeP, Node nodeN, double capacity) {
        super(name, nodeP, nodeN);
        this.capacity = capacity;
        this.V_p = nodeP.getVoltage();
        this.V_n = nodeN.getVoltage();
    }

    @Override
    public void updateElementCurrent() {
        current = capacity * (nodeP.getVoltage() - V_p - nodeN.getVoltage() + V_n)
                / InputController.getInstance().getDeltaT();
        V_p = nodeP.getVoltage();
        V_n = nodeN.getVoltage();
    }

    @Override
    public double getValue() {
        return capacity;
    }

    @Override
    public String getType() {
        return "capacitor";
    }

}
