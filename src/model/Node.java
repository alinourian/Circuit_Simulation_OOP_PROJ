package model;

import java.util.ArrayList;

public class Node {
    private final String name;
    private int voltage;
    private ArrayList<Node> neighborNodes;

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
}
