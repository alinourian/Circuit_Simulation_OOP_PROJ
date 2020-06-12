package model;

public class Inductor extends Element{
    private double leadInductance;

    public Inductor(String name, Node nodeP, Node nodeN, double leadInductance) {
        super(name, nodeP, nodeN);
        this.leadInductance = leadInductance;
    }
}
