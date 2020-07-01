package model;

import controller.InputController;

public class Capacitor extends Element {
    private final double capacity;
    private double V_p;
    private double V_n;
    private double help_V_p;
    private double help_V_n;
    private double[] currentAtEachStep;

    public Capacitor(String name, Node nodeP, Node nodeN, double capacity) {
        super(name, nodeP, nodeN);
        this.capacity = capacity;
        this.V_p = nodeP.getVoltage();
        this.V_n = nodeN.getVoltage();
    }

    @Override
    public void updateElementCurrent() {
        helpCurrent = current;
        current = capacity * (nodeP.getVoltage() - V_p - nodeN.getVoltage() + V_n)
                / InputController.getInstance().getDeltaT();
        help_V_p = V_p;
        help_V_n = V_n;
        V_p = nodeP.getVoltage();
        V_n = nodeN.getVoltage();
    }

    @Override
    public void setBackElementCurrent() {
        current = helpCurrent;
        V_p = help_V_p;
        V_n = help_V_n;
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
