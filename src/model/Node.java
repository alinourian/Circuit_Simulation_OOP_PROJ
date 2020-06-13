package model;

import java.util.ArrayList;

public class Node {
    protected final String name;
    protected int voltage = 0;
    protected ArrayList<Node> neighborNodes;
    protected ArrayList<Double> currents = new ArrayList<>();
    protected ArrayList<Element> elements = new ArrayList<>();
    protected ArrayList<Source> sources = new ArrayList<>();

    public Node (String name) {
        this.name = name;
        neighborNodes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getVoltage() {
        return voltage;
    }

    public ArrayList<Node> getNeighborNodes() {
        return neighborNodes;
    }

    public void setNeighborNodes(ArrayList<Node> neighborNodes) {
        this.neighborNodes = neighborNodes;
    }

    public ArrayList<Double> getCurrents() {
        return currents;
    }

    public void setCurrents(ArrayList<Double> currents) {
        this.currents = currents;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public ArrayList<Source> getSources() {
        return sources;
    }
}
