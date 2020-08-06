package model;

import enums.Type;

public class Diode extends Element {
    private boolean hasCurrent;

    public Diode(String name, Node nodeP, Node nodeN) {
        super(name, nodeP, nodeN);
    }

    @Override
    public void updateElementCurrent() {
        //helpCurrent = current;
        //current = (nodeP.getVoltage() - nodeN.getVoltage());
        //nodeN.setVoltage(nodeP.getVoltage());
    }

    @Override
    public double getCurrent(Node node) {
        Node other = node.equals(nodeP) ? nodeN : nodeP;
        double sumCurrents = 0;
        if (nodeP.getVoltage() - nodeN.getVoltage() >= 0) {
            for (Element element : other.getElements()) {
                if (!(element instanceof Diode)) {
                    sumCurrents += element.getCurrent(other);
                }
            }
            for (Source source : other.getSources()) {
                sumCurrents += source.getCurrent(other);
            }
        }
        return sumCurrents;
    }

    @Override
    public void setBackElementCurrent() {
        current = helpCurrent;
    }

    @Override
    public Type getType() {
        return Type.DIODE;
    }
}
