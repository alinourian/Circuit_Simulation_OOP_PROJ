package model;

public class Diode extends Element {
    private boolean hasCurrent;

    public Diode(String name, Node nodeP, Node nodeN) {
        super(name, nodeP, nodeN);
    }
}
