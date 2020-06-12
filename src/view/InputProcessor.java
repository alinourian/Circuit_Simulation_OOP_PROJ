package view;

import controller.Controller;
import model.*;

public abstract class InputProcessor {
    private static final Controller controller = Controller.getInstance();
    public static int COMMANDLINE = 0;

    public static void inputProcess(String command, int commandLine) {
        InputProcessor.COMMANDLINE = commandLine;
        String[] split = command.split("\\s+");
        if (CommandsRegex.COMMENT_COMMAND.getMatcher(command).matches()) {
            System.out.println(commandLine + ".Comment line");
        } else if (CommandsRegex.RESISTOR.getMatcher(command).matches()) {
            addResistor(split[0], split[1], split[2], split[3]);
        } else if (CommandsRegex.CAPACITOR.getMatcher(command).matches()) {
            addCapacitor(split[0], split[1], split[2], split[3]);
        } else if (CommandsRegex.INDUCTOR.getMatcher(command).matches()) {
            addInductor(split[0], split[1], split[2], split[3]);
        } else if (CommandsRegex.CURRENT_SOURCE.getMatcher(command).matches()) {
            addCurrentSource(split[0], split[1], split[2], split[3], split[4], split[5], split[6]);
        } else if (CommandsRegex.VOLTAGE_CONTROLLED_CURRENT_SOURCE.getMatcher(command).matches()) {
            addVoltageControlledCurrentSource(split[0], split[1], split[2], split[3], split[4], split[5]);
        } else if (CommandsRegex.CURRENT_CONTROLLED_CURRENT_SOURCE.getMatcher(command).matches()) {
            addCurrentControlledCurrentSource(split[0], split[1], split[2], split[3], split[4]);
        } else if (CommandsRegex.VOLTAGE_SOURCE.getMatcher(command).matches()) {
            addVoltageSource(split[0], split[1], split[2], split[3], split[4], split[5], split[6]);
        } else if (CommandsRegex.VOLTAGE_CONTROLLED_VOLTAGE_SOURCE.getMatcher(command).matches()) {
            addVoltageControlledVoltageSource(split[0], split[1], split[2], split[3], split[4], split[5]);
        } else if (CommandsRegex.CURRENT_CONTROLLED_VOLTAGE_SOURCE.getMatcher(command).matches()) {
            addCurrentControlledVoltageSource(split[0], split[1], split[2], split[3], split[4]);
        }
    }

    private static void addResistor(String name, String node1, String node2, String s_value) {
        if (controller.findResistor(name) != null) {
            System.err.println("error :");
            System.err.println("Line " + InputProcessor.COMMANDLINE + " : Similar name found!");
        } else {
            Node nodeP = new Node(node1);
            Node nodeN = new Node(node2);
            char unit = s_value.charAt(s_value.length() - 1);
            double factor = controller.getUnit(unit);
            double value;
            try {
                if (factor == -1) {
                    value = Double.parseDouble(s_value);
                } else if (factor == -2) {
                    System.err.println("error :");
                    System.err.println("Line " + InputProcessor.COMMANDLINE + " : Invalid value!");
                    return;
                } else {
                    value = Double.parseDouble(s_value.substring(0, s_value.length() - 1)) * factor;
                }
            } catch (NumberFormatException e) {
                System.err.println("error :");
                System.err.println("Line " + InputProcessor.COMMANDLINE + " : Invalid value!");
                return;
            }
            if (value < 0) {
                System.err.println("error :");
                System.err.println("Line " + InputProcessor.COMMANDLINE + " : Invalid value!");
            } else {
                Resistor resistor = new Resistor(name, nodeP, nodeN, value);
                controller.addResistor(resistor);
            }
        }
    }

    private static void addCapacitor(String name, String node1, String node2, String s_value) {
        if (controller.findCapacitor(name) != null) {
            System.err.println("error :");
            System.err.println("Line " + InputProcessor.COMMANDLINE + " : Similar name found!");
        } else {
            Node nodeP = new Node(node1);
            Node nodeN = new Node(node2);
            char unit = s_value.charAt(s_value.length() - 1);
            double factor = controller.getUnit(unit);
            double value;
            try {
                if (factor == -1) {
                    value = Double.parseDouble(s_value);
                } else if (factor == -2) {
                    System.err.println("error :");
                    System.err.println("Line " + InputProcessor.COMMANDLINE + " : Invalid value!");
                    return;
                } else {
                    value = Double.parseDouble(s_value.substring(0, s_value.length() - 1)) * factor;
                }
            } catch (NumberFormatException e) {
                System.err.println("error :");
                System.err.println("Line " + InputProcessor.COMMANDLINE + " : Invalid value!");
                return;
            }
            if (value < 0) {
                System.err.println("error :");
                System.err.println("Line " + InputProcessor.COMMANDLINE + " : Invalid value!");
            } else {
                Capacitor capacitor = new Capacitor(name, nodeP, nodeN, value);
                controller.addCapacitor(capacitor);
            }
        }
    }

    private static void addInductor(String name, String node1, String node2, String s_value) {
        if (controller.findInductor(name) != null) {
            System.err.println("error :");
            System.err.println("Line " + InputProcessor.COMMANDLINE + " : Similar name found!");
        } else {
            Node nodeP = new Node(node1);
            Node nodeN = new Node(node2);
            char unit = s_value.charAt(s_value.length() - 1);
            double factor = controller.getUnit(unit);
            double value;
            try {
                if (factor == -1) {
                    value = Double.parseDouble(s_value);
                } else if (factor == -2) {
                    System.err.println("error :");
                    System.err.println("Line " + InputProcessor.COMMANDLINE + " : Invalid value!");
                    return;
                } else {
                    value = Double.parseDouble(s_value.substring(0, s_value.length() - 1)) * factor;
                }
            } catch (NumberFormatException e) {
                System.err.println("error :");
                System.err.println("Line " + InputProcessor.COMMANDLINE + " : Invalid value!");
                return;
            }
            if (value < 0) {
                System.err.println("error :");
                System.err.println("Line " + InputProcessor.COMMANDLINE + " : Invalid value!");
            } else {
                Inductor inductor = new Inductor(name, nodeP, nodeN, value);
                controller.addInductor(inductor);
            }
        }
    }

    private static void addCurrentSource(String name, String node1, String node2, String s_value,
                                         String s_amplitude, String s_frequency, String s_phase) {
        if (controller.findCurrentSource(name) != null) {
            System.err.println("error :");
            System.err.println("Line " + InputProcessor.COMMANDLINE + " : Similar name found!");
        } else {
            Node nodeP = new Node(node1);
            Node nodeN = new Node(node2);
            double valueFactor = controller.getUnit(s_value.charAt(s_value.length() - 1));
            double amplitudeFactor = controller.getUnit(s_amplitude.charAt(s_amplitude.length() - 1));
            double frequencyFactor = controller.getUnit(s_frequency.charAt(s_frequency.length() - 1));
            double phaseFactor = controller.getUnit(s_phase.charAt(s_phase.length() - 1));
            if (valueFactor == -2 || amplitudeFactor == -2 || frequencyFactor == -2 || phaseFactor == -2) {
                System.err.println("error :");
                System.err.println("Line " + InputProcessor.COMMANDLINE + " : Invalid value!");
                return;
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
                controller.addCurrentSource(currentSource);
            } catch (NumberFormatException e) {
                System.err.println("error :");
                System.err.println("Line " + InputProcessor.COMMANDLINE + " : Invalid value!");
            }
        }
    }

    private static void addVoltageControlledCurrentSource(String name, String node1, String node2,
                                                          String voltage1, String voltage2, String gain) {

    }

    private static void addCurrentControlledCurrentSource(String name, String node1, String node2,
                                                          String branch, String gain) {

    }

    private static void addVoltageSource(String name, String node1, String node2, String s_value,
                                         String s_Amplitude, String s_Frequency, String s_Phase) {

    }

    private static void addVoltageControlledVoltageSource(String name, String node1, String node2,
                                                          String voltage1, String voltage2, String gain) {

    }

    private static void addCurrentControlledVoltageSource(String name, String node1, String node2,
                                                          String branch, String gain) {

    }
}
