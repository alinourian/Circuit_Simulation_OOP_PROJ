package controller;

import model.*;

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

    public void addResistor(Resistor resistor) {

    }

    public void addCapacitor(Capacitor capacitor) {

    }

    public void addInductor(Inductor inductor) {

    }

    public void addCurrentSource(CurrentSource currentSource) {

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

    public double getUnit(char unit) {
        switch (unit) {
            case 'p':
                return Math.pow(10, -12);
            case 'n':
                return Math.pow(10, -9);
            case 'u':
                return Math.pow(10, -6);
            case 'm':
                return Math.pow(10, -3);
            case 'k':
                return Math.pow(10, 3);
            case 'M':
                return Math.pow(10, 6);
            case 'G':
                return Math.pow(10, 9);
            case '0' | '1' | '2' | '3' | '4' |
                    '5' | '6' | '7' | '8' | '9':
                return -1;
            default:
                return -2;
        }
    }


}
