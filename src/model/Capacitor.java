package model;

public class Capacitor extends Element {
    private double capacity;

    public Capacitor(String name, Node nodeP, Node nodeN, double capacity) {
        super(name, nodeP, nodeN);
        this.capacity = capacity;
        this.current = 0;
    }

    @Override
    public String getType() {
        return "capacitor";
    }

}
