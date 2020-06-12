package view;

public abstract class InputProcessor {
    public static void inputProcess(String command, int commandLine) {
        if (CommandsRegex.COMMENT_COMMAND.getMatcher(command).matches()) {
            System.out.println(commandLine + ".Comment line");
        } else if (CommandsRegex.RESISTOR.getMatcher(command).matches()) {
            String[] split = command.split("\\s+");

        } else if (CommandsRegex.CAPACITOR.getMatcher(command).matches()) {

        } else if (CommandsRegex.INDUCTOR.getMatcher(command).matches()) {

        } else if (CommandsRegex.CURRENT_SOURCE.getMatcher(command).matches()) {

        } else if (CommandsRegex.VOLTAGE_CONTROLLED_CURRENT_SOURCE.getMatcher(command).matches()) {

        } else if (CommandsRegex.CURRENT_CONTROLLED_CURRENT_SOURCE.getMatcher(command).matches()) {

        } else if (CommandsRegex.VOLTAGE_SOURCE.getMatcher(command).matches()) {

        } else if (CommandsRegex.VOLTAGE_CONTROLLED_VOLTAGE_SOURCE.getMatcher(command).matches()) {

        } else if (CommandsRegex.CURRENT_CONTROLLED_VOLTAGE_SOURCE.getMatcher(command).matches()) {

        }
    }

    private static void addResistor(String command) {

    }

}
