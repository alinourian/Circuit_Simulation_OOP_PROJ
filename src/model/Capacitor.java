package model;

import controller.InputController;
import controller.Solver;
import enums.Type;

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
        double lastVoltageP;
        double lastVoltageN;
        if (Solver.step == 0) {
            lastVoltageP = 0;
            lastVoltageN = 0;
        } else {
            lastVoltageP = nodeP.getVoltages().get(Solver.step - 1);
            lastVoltageN = nodeN.getVoltages().get(Solver.step - 1);
        }
        current = capacity * (nodeP.getVoltage() - lastVoltageP - nodeN.getVoltage() + lastVoltageN)
                / InputController.getInstance().getDeltaT();
        //help_V_p = V_p;
        //help_V_n = V_n;
        //V_p = nodeP.getVoltage();
        //V_n = nodeN.getVoltage();
    }

    @Override
    public void setBackElementCurrent() {
        current = helpCurrent;
        //V_p = help_V_p;
        //V_n = help_V_n;
    }

    @Override
    public double getValue() {
        return capacity;
    }

    @Override
    public Type getType() {
        return Type.CAPACITOR;
    }

    @Override
    public String toString() {
        return  name + "       " +
                nodeP + "       " +
                nodeN + "       " +
                capacity;
    }

}
