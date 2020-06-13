package model;

import controller.InputController;

public class Inductor extends Element{
    private double inductance;
    private double V_p;
    private double V_n;

    public Inductor(String name, Node nodeP, Node nodeN, double inductance) {
        super(name, nodeP, nodeN);
        this.inductance = inductance;
        this.V_p = nodeP.getVoltage();
        this.V_n = nodeN.getVoltage();
    }

    @Override
    public void updateElementCurrent() {
        current = current + ((nodeP.getVoltage() - V_p - nodeN.getVoltage() + V_n)
                * InputController.getInstance().getDeltaT()) / inductance;
        V_p = nodeP.getVoltage();
        V_n = nodeN.getVoltage();
    }

    @Override
    public double getValue() {
        return inductance;
    }

    @Override
    public String getType() {
        return "inductor";
    }
}
