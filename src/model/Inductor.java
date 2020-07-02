package model;

import controller.InputController;
import enums.Type;

public class Inductor extends Element{
    private final double inductance;
    private double V_p;
    private double V_n;
    private double[] voltageAtEachStep;

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
    public Type getType() {
        return Type.INDUCTOR;
    }

    @Override
    public String toString() {
        return  name + "       " +
                nodeP + "       " +
                nodeN + "       " +
                inductance;
    }
}
