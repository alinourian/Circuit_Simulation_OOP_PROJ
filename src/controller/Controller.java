package controller;

import model.*;
import view.FileInputProcessor;

import java.util.ArrayList;
import java.util.Map;

public class Controller {
    private static Controller instance;

    public static Controller getInstance() {
        if(instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    ArrayList<Node> nodes = new ArrayList<>();
    ArrayList<Resistor> resistors = new ArrayList<>();
    ArrayList<Capacitor> capacitors = new ArrayList<>();
    ArrayList<Inductor> inductors = new ArrayList<>();
    ArrayList<CurrentSource> currentSources = new ArrayList<>();
    ArrayList<VoltageSource> voltageSources = new ArrayList<>();

    private double deltaV = 0;
    private double deltaI = 0;
    private double deltaT = 0;
    private double tranTime = 0;

    public void addElement(Element element) {
        if (findNode(element.getNodeP().getName()) == null) {
            nodes.add(element.getNodeP());
        }
        if (findNode(element.getNodeN().getName()) == null) {
            nodes.add(element.getNodeN());
        }
        if (!element.getNodeP().getNeighborNodes().contains(element.getNodeN())) {
            element.getNodeP().getNeighborNodes().add(element.getNodeN());
        }
        if (!element.getNodeN().getNeighborNodes().contains(element.getNodeP())) {
            element.getNodeN().getNeighborNodes().add(element.getNodeP());
        }
        if (element.getType().equalsIgnoreCase("resistor")) {
            resistors.add((Resistor) element);
        } else if (element.getType().equalsIgnoreCase("capacitor")) {
            capacitors.add((Capacitor) element);
        } else if (element.getType().equalsIgnoreCase("inductor")) {
            inductors.add((Inductor) element);
        } else if (element.getType().equalsIgnoreCase("currentSource")) {
            currentSources.add((CurrentSource) element);
        } else if (element.getType().equalsIgnoreCase("voltageSource")) {
            voltageSources.add((VoltageSource) element);
        }
    }

    public Node findNode(String name) {
        for (Node node : nodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    public Resistor findResistor(String name) {
        for (Resistor resistor : resistors) {
            if (resistor.getName().equals(name)) {
                return resistor;
            }
        }
        return null;
    }

    public Capacitor findCapacitor(String name) {
        for (Capacitor capacitor : capacitors) {
            if (capacitor.getName().equals(name)) {
                return capacitor;
            }
        }
        return null;
    }

    public Inductor findInductor(String name) {
        for (Inductor inductor : inductors) {
            if (inductor.getName().equals(name)) {
                return inductor;
            }
        }
        return null;
    }

    public CurrentSource findCurrentSource(String name) {
        for (CurrentSource currentSource : currentSources) {
            if (currentSource.getName().equals(name)) {
                return currentSource;
            }
        }
        return null;
    }

    public VoltageSource findVoltageSource(String name) {
        for (VoltageSource voltageSource : voltageSources) {
            if (voltageSource.getName().equals(name)) {
                return voltageSource;
            }
        }
        return null;
    }

    public double getValueOfString(String string) {
        char unit = string.charAt(string.length() - 1);
        double factor = getUnit(unit);
        double value;
        try {
            if (factor == -2) {
                return -1;
            } else if (factor == -1) {
                value = Double.parseDouble(string);
            } else {
                value = Double.parseDouble(string.substring(0, string.length() - 1)) * factor;
            }
        } catch (NumberFormatException e) {
            return -1;
        }
        if (value < 0) {
            return -1;
        }
        return value;
    }

    public double getUnit(char unit) {
        if (unit == 'p') {
            return Math.pow(10, -12);
        } else if (unit == 'n') {
            return Math.pow(10, -9);
        } else if (unit == 'u') {
            return Math.pow(10, -6);
        } else if (unit == 'm') {
            return Math.pow(10, -3);
        } else if (unit == 'k') {
            return Math.pow(10, 3);
        } else if (unit == 'M') {
            return Math.pow(10, 6);
        } else if (unit == 'G') {
            return Math.pow(10, 9);
        } else if (unit == '0' || unit == '1' || unit == '2' || unit == '3' || unit == '4' ||
                unit == '5' || unit == '6' || unit == '7' || unit == '8' || unit == '9') {
            return -1;
        } else {
            return -2;
        }
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public double getDeltaV() {
        return deltaV;
    }

    public double getDeltaI() {
        return deltaI;
    }

    public double getDeltaT() {
        return deltaT;
    }

    public double getTranTime() {
        return tranTime;
    }

    public void setDeltaV(double deltaV) {
        this.deltaV = deltaV;
    }

    public void setDeltaI(double deltaI) {
        this.deltaI = deltaI;
    }

    public void setDeltaT(double deltaT) {
        this.deltaT = deltaT;
    }

    public void setTranTime(double tranTime) {
        this.tranTime = tranTime;
    }
}
