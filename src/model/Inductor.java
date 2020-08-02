package model;

import controller.InputController;
import controller.Solver;
import enums.Type;

public class Inductor extends Element{
    private final double inductance;
    private final InputController controller = InputController.getInstance();

    public Inductor(String name, Node nodeP, Node nodeN, double inductance) {
        super(name, nodeP, nodeN);
        this.inductance = inductance;
    }

    @Override
    public void updateElementCurrent() {
        helpCurrent = current;
        current = 0;
        for (int i = 0; i < Solver.step; i++) {
            current += (1 / inductance) * (nodeP.getVoltages().get(i) - nodeN.getVoltages().get(i)) * controller.getDeltaT();
        }
    }

    @Override
    public void setBackElementCurrent() {
        current = helpCurrent;
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
