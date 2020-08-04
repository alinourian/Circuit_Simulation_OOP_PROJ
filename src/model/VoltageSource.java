package model;

import controller.Solver;
import enums.Type;

public class VoltageSource extends Source {
    private double voltageDC;
    private double amplitude;
    private double frequency;
    private double phase;

    public VoltageSource(String name, Node nodeP, Node nodeN, double voltageDC,
                         double amplitude, double frequency, double phase) {
        super(name, nodeP, nodeN);
        this.voltageDC = voltageDC;
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.phase = phase;
    }

    public VoltageSource(String name, Node nodeP, Node nodeN) {
        super(name, nodeP, nodeN);
    }

    public double getCurrentForVSource(Node node1, Node node2) {
        double totalCurrent = 0 ;

        for (Element element : node1.getElements()) {
            totalCurrent += element.getCurrent(node1);
        }
        for (Source source : node1.getSources()) {
            if (source instanceof CurrentSource) {
                totalCurrent += source.getCurrent(node1);
            } else {
                VoltageSource vSource = (VoltageSource) source;
                Node node0 = vSource.getNodeP().getName().equals(node1.getName()) ? vSource.getNodeN() : vSource.getNodeP();
                if (node0 != node2) {
                    totalCurrent += vSource.getCurrentForVSource(node0, node1);
                }
            }
        }
        return -1 * totalCurrent;
    }

    @Override
    public double getVoltage(Node node) {
        double value = voltageDC + amplitude * Math.sin(2 * Math.PI * frequency * Solver.time + phase);
        if (node.getName().equals(nodeP.getName())) {
            return value;
        } else {
            return -1 * value;
        }
    }

    @Override
    public double getValue(Node node) {
        return getVoltage(node);
    }

    @Override
    public Type getType() {
        return Type.VOLTAGE_SOURCE;
    }

    @Override
    public String toString() {
        return  name + "       " +
                nodeP + "       " +
                nodeN + "       " +
                voltageDC + "       " +
                amplitude + "       " +
                frequency + "       " +
                phase + "       ";
    }

}
