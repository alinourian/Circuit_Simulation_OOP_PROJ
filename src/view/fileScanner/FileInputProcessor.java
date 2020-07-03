package view.fileScanner;

import controller.InputController;
import enums.Type;

public abstract class FileInputProcessor {
    private static final InputController controller = InputController.getInstance();
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
        } else if (CommandsRegex.DELTA_VOLTAGE.getMatcher(command).matches()) {
            return addDeltaVoltage(split[1]);
        } else if (CommandsRegex.DELTA_CURRENT.getMatcher(command).matches()) {
            return addDeltaCurrent(split[1]);
        } else if (CommandsRegex.DELTA_TIME.getMatcher(command).matches()) {
            return addDeltaTime(split[1]);
        } else if (CommandsRegex.TRAN.getMatcher(command).matches()) {
            return addTran(split[1]);
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
        double value = controller.getValueOfString(s_value);
        if (value == -1) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        controller.addElement(name, node1, node2, value, Type.RESISTOR);
        return true;
    }

    private static boolean addCapacitor(String name, String node1, String node2, String s_value) {
        if (controller.findCapacitor(name) != null) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Similar name found!");
            return false;
        }
        double value = controller.getValueOfString(s_value);
        if (value == -1) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        controller.addElement(name, node1, node2, value, Type.CAPACITOR);
        return true;
    }

    private static boolean addInductor(String name, String node1, String node2, String s_value) {
        if (controller.findInductor(name) != null) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Similar name found!");
            return false;
        }
        double value = controller.getValueOfString(s_value);
        if (value == -1) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        controller.addElement(name, node1, node2, value, Type.INDUCTOR);
        return true;
    }

    private static boolean addCurrentSource(String name, String node1, String node2, String s_value,
                                         String s_amplitude, String s_frequency, String s_phase) {
        if (controller.findCurrentSource(name) != null) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Similar name found!");
            return false;
        }
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
            controller.addSource(name, node1, node2, value, amplitude, frequency, phase, Type.CURRENT_SOURCE);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
    }

    //check node before add all nodes!!
    private static boolean addVoltageControlledCurrentSource(String name, String node1, String node2,
                                                          String voltage1, String voltage2, String value) {
        if (controller.findCurrentSource(name) != null) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Similar name found!");
            return false;
        }
        double valueFactor = controller.getUnit(value.charAt(value.length() - 1));
        if (valueFactor == -2) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        if (controller.findNode(voltage1) == null && controller.findNode(voltage2) == null) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Controller node does not initialize!");
            return false;
        }
        try {
            double gain = valueFactor == -1 ? Double.parseDouble(value) :
                    Double.parseDouble(value.substring(0, value.length() - 1)) * valueFactor;
            controller.addVoltageControlledSource(name, node1, node2, gain, voltage1, voltage2, Type.V_C_C_S);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
    }

    private static boolean addCurrentControlledCurrentSource(String name, String node1, String node2,
                                                          String branch, String value) {
        if (controller.findCurrentSource(name) != null) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Similar name found!");
            return false;
        }
        double valueFactor = controller.getUnit(value.charAt(value.length() - 1));
        if (valueFactor == -2) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        if (controller.findElement(branch) == null && controller.findSource(branch) == null) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Controller branch does not initialize!");
            return false;
        }
        try {
            double gain = valueFactor == -1 ? Double.parseDouble(value) :
                    Double.parseDouble(value.substring(0, value.length() - 1)) * valueFactor;
            controller.addCurrentControlledSource(name, node1, node2, gain, branch, Type.C_C_C_S);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
    }

    private static boolean addVoltageSource(String name, String node1, String node2, String s_value,
                                         String s_amplitude, String s_frequency, String s_phase) {
        if (controller.findVoltageSource(name) != null) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Similar name found!");
            return false;
        }
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
            controller.addSource(name, node1, node2, value, amplitude, frequency, phase, Type.VOLTAGE_SOURCE);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
    }

    private static boolean addVoltageControlledVoltageSource(String name, String node1, String node2,
                                                          String voltage1, String voltage2, String value) {
        if (controller.findVoltageSource(name) != null) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Similar name found!");
            return false;
        }
        double valueFactor = controller.getUnit(value.charAt(value.length() - 1));
        if (valueFactor == -2) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        if (controller.findNode(voltage1) == null && controller.findNode(voltage2) == null) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Controller node does not initialize!");
            return false;
        }
        try {
            double gain = valueFactor == -1 ? Double.parseDouble(value) :
                    Double.parseDouble(value.substring(0, value.length() - 1)) * valueFactor;
            controller.addVoltageControlledSource(name, node1, node2, gain, voltage1, voltage2, Type.V_C_V_S);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
    }

    private static boolean addCurrentControlledVoltageSource(String name, String node1, String node2,
                                                          String branch, String value) {
        if (controller.findVoltageSource(name) != null) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Similar name found!");
            return false;
        }
        double valueFactor = controller.getUnit(value.charAt(value.length() - 1));
        if (valueFactor == -2) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        if (controller.findElement(branch) == null && controller.findSource(branch) == null) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Controller branch does not initialize!");
            return false;
        }
        try {
            double gain = valueFactor == -1 ? Double.parseDouble(value) :
                    Double.parseDouble(value.substring(0, value.length() - 1)) * valueFactor;
            controller.addCurrentControlledSource(name, node1, node2, gain, branch, Type.C_C_V_S);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
    }

    private static boolean addDeltaVoltage(String deltaVoltage) {
        double value = controller.getValueOfString(deltaVoltage);
        if (value == -1) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        controller.setDeltaV(value);
        return true;
    }

    private static boolean addDeltaCurrent(String deltaCurrent) {
        double value = controller.getValueOfString(deltaCurrent);
        if (value == -1) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        controller.setDeltaI(value);
        return true;
    }

    private static boolean addDeltaTime(String deltaTime) {
        double value = controller.getValueOfString(deltaTime);
        if (value == -1) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        controller.setDeltaT(value);
        return true;
    }

    private static boolean addTran(String time) {
        double value = controller.getValueOfString(time);
        if (value == -1) {
            System.err.println("error :");
            System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Invalid value!");
            return false;
        }
        controller.setTranTime(value);
        return true;
    }
}
