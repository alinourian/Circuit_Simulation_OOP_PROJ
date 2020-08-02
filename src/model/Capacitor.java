package model;

import controller.InputController;
import controller.Solver;
import enums.Type;

public class Capacitor extends Element {
    private final double capacity;

    public Capacitor(String name, Node nodeP, Node nodeN, double capacity) {
        super(name, nodeP, nodeN);
        this.capacity = capacity;
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
    }

    @Override
    public void setBackElementCurrent() {
        current = helpCurrent;
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
