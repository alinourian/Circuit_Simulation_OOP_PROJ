package model;

import controller.InputController;

import java.util.ArrayList;

public class Node {
    protected final String name;
    protected double voltage;
    protected ArrayList<Node> neighborNodes;
    protected ArrayList<Element> elements;
    protected ArrayList<Source> sources;
    private Node parentNode = null;
    private Union includingUnion;
    private int isVisited;

    public Node (String name) {
        this.name = name;
        this.voltage = 0;
        this.isVisited = 0;
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
            if (source instanceof CurrentSource) {
                CurrentSource currentSource = (CurrentSource)source;
                totalCurrent += currentSource.getCurrent(InputController.getInstance().findNode(name), time);
            }
        }
        return totalCurrent;
    }


    public boolean getIsVisited() {
        if(isVisited == 0)
            return false;
        else
            return true;

    }

    public Node getParentNode() { return parentNode; }

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

    public void setIncludingUnion(Union includingUnion) { this.includingUnion = includingUnion; }

    public void setVisited() { this.isVisited = 1; }

    public void setNotVisited() {this.isVisited = 0; }

    public void setParentNode(Node parentNode) { this.parentNode = parentNode; }

    public void setVoltage(double voltage) {
        if (!name.equals("0")) {
            this.voltage = voltage;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
