package model;

import controller.InputController;

import java.util.ArrayList;

public class Node {
    private final String name;
    private double voltage;
    private double saveVoltage;
    private ArrayList<Node> neighborNodes;
    private ArrayList<Element> elements;
    private ArrayList<Source> sources;
    private ArrayList<Double> voltages;
    private Node parentNode = null;
    private Union includingUnion;
    private int isVisited;


    public Node (String name) {
        this.name = name;
        this.voltage = 0;
        this.saveVoltage = 0;
        this.isVisited = 0;
        this.neighborNodes = new ArrayList<>();
        this.elements = new ArrayList<>();
        this.sources = new ArrayList<>();
        this.voltages = new ArrayList<>();
        voltages.add(0.0);
    }

    public double getTotalCurrent(double time){
        double totalCurrent = 0;
        for (Element element : elements){
            totalCurrent += element.getCurrent(InputController.getInstance().findNode(name));
        }
        for (Source source : sources) {
            if (source instanceof CurrentSource) {
                CurrentSource currentSource = (CurrentSource)source;
                totalCurrent += currentSource.getCurrent(InputController.getInstance().findNode(name));
            }
        }
        return totalCurrent;
    }


    public boolean getIsVisited() {
        return isVisited != 0;

    }

    public double getSaveVoltage() {
        return saveVoltage;
    }

    public Node getParentNode() { return parentNode; }

    public String getName() { return name; }

    public Union getIncludingUnion() { return includingUnion; }

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

    public ArrayList<Double> getVoltages() {
        return voltages;
    }

    public void setSaveVoltage(double saveVoltage) {
        this.saveVoltage = saveVoltage;
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
