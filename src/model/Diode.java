package model;

import enums.Type;

public class Diode extends Element {
    private boolean hasCurrent;

    public Diode(String name, Node nodeP, Node nodeN) {
        super(name, nodeP, nodeN);
    }

    @Override
    public Type getType() {
        return Type.DIODE;
    }
}
