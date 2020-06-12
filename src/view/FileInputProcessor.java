package view;

import controller.Controller;
import model.*;

public abstract class FileInputProcessor {
    private static final Controller controller = Controller.getInstance();
    public static int COMMANDLINE = 0;

    public static boolean inputProcess(String command, int commandLine) {
        FileInputProcessor.COMMANDLINE = commandLine;
        String[] split = command.split("\\s+");
        if (CommandsRegex.COMMENT_COMMAND.getMatcher(command).matches()) {
            return true;
        } else if (CommandsRegex.RESISTOR.getMatcher(command).matches()) {
            return addResistor(split[0], split[1], split[2], split[3]);
        } else if (CommandsRegex.CAPACITOR.getMatcher(command).matches()) {
            return addCapacitor(split[0], split[1], split[2], split[3]);
        } else if (CommandsRegex.INDUCTOR.getMatcher(command).matches()) {
            return addInductor(split[0], split[1], split[2], split[3]);
        } else if (CommandsRegex.CURRENT_SOURCE.getMatcher(command).matches()) {
            return addCurrentSource(split[0], split[1], split[2], split[3], split[4], split[5], split[6]);
        } else if (CommandsRegex.VOLTAGE_CONTROLLED_CURRENT_SOURCE.getMatcher(command).matches()) {
            return addVoltageControlledCurrentSource(split[0], split[1], split[2], split[3], split[4], split[5]);
        } else if (CommandsRegex.CURRENT_CONTROLLED_CURRENT_SOURCE.getMatcher(command).matches()) {
            return addCurrentControlledCurrentSource(split[0], split[1], split[2], split[3], split[4]);
        } else if (CommandsRegex.VOLTAGE_SOURCE.getMatcher(command).matches()) {
            return addVoltageSource(split[0], split[1], split[2], split[3], split[4], split[5], split[6]);
        } else if (CommandsRegex.VOLTAGE_CONTROLLED_VOLTAGE_SOURCE.getMatcher(command).matches()) {
            return addVoltageControlledVoltageSource(split[0], split[1], split[2], split[3], split[4], split[5]);
        } else if (CommandsRegex.CURRENT_CONTROLLED_VOLTAGE_SOURCE.getMatcher(command).matches()) {
            return addCurrentControlledVoltageSource(split[0], split[1], split[2], split[3], split[4]);
        } else {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid command!");
            return false;
        }
    }

    private static boolean addResistor(String name, String node1, String node2, String s_value) {
        if (controller.findResistor(name) != null) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Similar name found!");
            return false;
        }
        Node nodeP = new Node(node1);
        Node nodeN = new Node(node2);
        char unit = s_value.charAt(s_value.length() - 1);
        double factor = controller.getUnit(unit);
        double value;
        try {
            if (factor == -2) {
                System.err.println("error :");
                System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
                return false;
            } else if (factor == -1) {
                value = Double.parseDouble(s_value);
            } else {
                value = Double.parseDouble(s_value.substring(0, s_value.length() - 1)) * factor;
            }
        } catch (NumberFormatException e) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        if (value < 0) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        Resistor resistor = new Resistor(name, nodeP, nodeN, value);
        controller.addElement(resistor);
        return true;
    }

    private static boolean addCapacitor(String name, String node1, String node2, String s_value) {
        if (controller.findCapacitor(name) != null) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Similar name found!");
            return false;
        }
        Node nodeP = new Node(node1);
        Node nodeN = new Node(node2);
        char unit = s_value.charAt(s_value.length() - 1);
        double factor = controller.getUnit(unit);
        double value;
        try {
            if (factor == -2) {
                System.err.println("error :");
                System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
                return false;
            } else if (factor == -1) {
                value = Double.parseDouble(s_value);
            } else {
                value = Double.parseDouble(s_value.substring(0, s_value.length() - 1)) * factor;
            }
        } catch (NumberFormatException e) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        if (value < 0) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        Capacitor capacitor = new Capacitor(name, nodeP, nodeN, value);
        controller.addElement(capacitor);
        return true;
    }

    private static boolean addInductor(String name, String node1, String node2, String s_value) {
        if (controller.findInductor(name) != null) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Similar name found!");
            return false;
        }
        Node nodeP = new Node(node1);
        Node nodeN = new Node(node2);
        char unit = s_value.charAt(s_value.length() - 1);
        double factor = controller.getUnit(unit);
        double value;
        try {
            if (factor == -2) {
                System.err.println("error :");
                System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
                return false;
            } else if (factor == -1) {
                value = Double.parseDouble(s_value);
            } else {
                value = Double.parseDouble(s_value.substring(0, s_value.length() - 1)) * factor;
            }
        } catch (NumberFormatException e) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        if (value < 0) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        Inductor inductor = new Inductor(name, nodeP, nodeN, value);
        controller.addElement(inductor);
        return true;
    }

    private static boolean addCurrentSource(String name, String node1, String node2, String s_value,
                                         String s_amplitude, String s_frequency, String s_phase) {
        if (controller.findCurrentSource(name) != null) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Similar name found!");
            return false;
        }
        Node nodeP = new Node(node1);
        Node nodeN = new Node(node2);
        double valueFactor = controller.getUnit(s_value.charAt(s_value.length() - 1));
        double amplitudeFactor = controller.getUnit(s_amplitude.charAt(s_amplitude.length() - 1));
        double frequencyFactor = controller.getUnit(s_frequency.charAt(s_frequency.length() - 1));
        double phaseFactor = controller.getUnit(s_phase.charAt(s_phase.length() - 1));
        if (valueFactor == -2 || amplitudeFactor == -2 || frequencyFactor == -2 || phaseFactor == -2) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        try {
            double value = valueFactor == -1 ? Double.parseDouble(s_value) :
                    Double.parseDouble(s_value.substring(0, s_value.length() - 1)) * valueFactor;
            double amplitude = amplitudeFactor == -1 ? Double.parseDouble(s_amplitude) :
                    Double.parseDouble(s_amplitude.substring(0, s_amplitude.length() - 1)) * amplitudeFactor;
            double frequency = frequencyFactor == -1 ? Double.parseDouble(s_frequency) :
                    Double.parseDouble(s_frequency.substring(0, s_amplitude.length() - 1)) * frequencyFactor;
            double phase = phaseFactor == -1 ? Double.parseDouble(s_phase) :
                    Double.parseDouble(s_phase.substring(0, s_phase.length() - 1)) * phaseFactor;
            CurrentSource currentSource = new CurrentSource(name, nodeP, nodeN, value, amplitude, frequency, phase);
            controller.addElement(currentSource);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
    }

    private static boolean addVoltageControlledCurrentSource(String name, String node1, String node2,
                                                          String voltage1, String voltage2, String gain) {
        return true;
    }

    private static boolean addCurrentControlledCurrentSource(String name, String node1, String node2,
                                                          String branch, String gain) {
        return true;
    }

    private static boolean addVoltageSource(String name, String node1, String node2, String s_value,
                                         String s_Amplitude, String s_Frequency, String s_Phase) {
        return true;
    }

    private static boolean addVoltageControlledVoltageSource(String name, String node1, String node2,
                                                          String voltage1, String voltage2, String gain) {
        return true;
    }

    private static boolean addCurrentControlledVoltageSource(String name, String node1, String node2,
                                                          String branch, String gain) {
        return true;
    }
}