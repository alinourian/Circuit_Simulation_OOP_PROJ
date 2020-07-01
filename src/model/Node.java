package model;

import controller.InputController;

import java.util.ArrayList;

public class Node {
    protected final String name;
    protected double voltage;
    boolean visited;
    protected ArrayList<Node> neighborNodes;
    protected ArrayList<Element> elements;
    protected ArrayList<Source> sources;
    private Node parentNode;
    private Union includingUnion;

    public Node (String name) {
        this.name = name;
        this.voltage = 0;
        this.visited = false;
        this.neighborNodes = new ArrayList<>();
        this.elements = new ArrayList<>();
        this.sources = new ArrayList<>();
    }

    public double getTotalCurrent(double time){
        double totalCurrent = 0;
        for (Element element : elements){
            totalCurrent += element.getCurrent(InputController.getInstance().findNode(name));
        }
        for (Source source : sources) {
            totalCurrent += source.getCurrent(InputController.getInstance().findNode(name), time);
        }
        return totalCurrent;
    }

    public static void resetNodes(){
        for (Node node: InputController.getInstance().getNodes()){
            node.visited = false;
        }
    }

    public String getName() {
        return name;
    }

    public double getVoltage() {
        return voltage;
    }

    public ArrayList<Node> getNeighborNodes() {
        return neighborNodes;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public ArrayList<Source> getSources() {
        return sources;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setVoltage(double voltage) {
        if (!name.equals("0")) {
            this.voltage = voltage;
        }
    }
}
