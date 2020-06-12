package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CommandsRegex {
    COMMENT_COMMAND("\\*.*"),
    //PART 1 - CURRENT SOURCES
    RESISTOR("(R\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*"),
    CAPACITOR("(C\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*"),
    INDUCTOR("(L\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*"),
    CURRENT_SOURCE("(I\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*"),
    VOLTAGE_CONTROLLED_CURRENT_SOURCE("(G\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*"),
    CURRENT_CONTROLLED_CURRENT_SOURCE("(F\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*"),
    //PART 2 - VOLTAGE SOURCES
    VOLTAGE_SOURCE("(V\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*"),
    VOLTAGE_CONTROLLED_VOLTAGE_SOURCE("(E\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*"),
    CURRENT_CONTROLLED_VOLTAGE_SOURCE("(H\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*"),
    //PART 3 - ANALYSIS PROPERTIES
    DELTA_VOLTAGE("dV\\s+(\\S+)\\s*"),
    DELTA_CURRENT("dI\\s+(\\S+)\\s*"),
    DELTA_TIME("dT\\s+(\\S+)\\s*"),
    TRAN("\\.tran\\s+(\\S+)\\s*");

    private final Pattern commandPattern;

    CommandsRegex(String commandString) {
        this.commandPattern = Pattern.compile(commandString);
    }

    public Matcher getMatcher(String input) {
        return this.commandPattern.matcher(input);
    }
}
