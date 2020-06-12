package model;

public class Node {
    private String name;
    private int voltage;

    public Node (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getVoltage() {
        return voltage;
    }
}
