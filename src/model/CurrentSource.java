package model;

import controller.Solver;
import enums.Type;

public class CurrentSource extends Source {
    protected double currentDC;
    protected double amplitude;
    protected double frequency;
    protected double phase;

    public CurrentSource(String name, Node nodeP, Node nodeN, double currentDC,
                         double amplitude, double frequency, double phase) {
        super(name, nodeP, nodeN);
        this.currentDC = currentDC;
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.phase = phase;
    }

    public CurrentSource(String name, Node nodeIn, Node nodeOut) {
        super(name, nodeIn, nodeOut);
    }

    @Override
    public double getCurrent(Node node) {
        double value = currentDC + amplitude * Math.sin(2 * Math.PI * frequency * Solver.time + phase);
        if (node.getName().equals(nodeP.getName())) {
            return value;
        } else {
            return -1 * value;
        }
    }

    @Override
    public double getValue(Node node) {
        return getCurrent(node);
    }

    public double getPhase() { return phase; }

    @Override
    public Type getType() {
        return Type.CURRENT_SOURCE;
    }

    @Override
    public String toString() {
        return  name + "       " +
                nodeP + "       " +
                nodeN + "       " +
                currentDC + "       " +
                amplitude + "       " +
                frequency + "       " +
                phase + "       ";
    }
}
