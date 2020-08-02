package model;

import controller.InputController;
import controller.Solver;
import enums.Type;

import javax.swing.*;

public class Inductor extends Element{
    private final double inductance;
    private double V_p;
    private double V_n;
    private final InputController controller = InputController.getInstance();
    private double[] voltageAtEachStep;

    public Inductor(String name, Node nodeP, Node nodeN, double inductance) {
        super(name, nodeP, nodeN);
        this.inductance = inductance;
        this.V_p = nodeP.getVoltage();
        this.V_n = nodeN.getVoltage();
    }

    @Override
    public void updateElementCurrent() {

        current = 0;

        for (int i = 1; i <= Solver.step; i++)
        {
            current += (1/inductance)*(nodeP.getVoltages().indexOf(i) - nodeN.getVoltages().indexOf(i))*controller.getDeltaT();
        }

        System.out.println("at step: "+Solver.step);
        System.out.println("node p getvoltages index of this step: "+nodeP.getVoltages().indexOf(Solver.step));
        System.out.println("node n getvoltages index of this step: "+nodeN.getVoltages().indexOf(Solver.step));
        System.out.println("voltage of node p is :"+nodeP.getVoltage());
        System.out.println("voltage of node n is :"+nodeN.getVoltage());
        System.out.println("inductance is : "+inductance);

        System.out.println("current of: "+name+" is: "+current);
        /*current = current + ((nodeP.getVoltage() - V_p - nodeN.getVoltage() + V_n)
                * InputController.getInstance().getDeltaT()) / inductance;
        V_p = nodeP.getVoltage();
        V_n = nodeN.getVoltage();*/
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
