package org.arifjehoh.View;

public class CommandLine {
    private static final String PARAM_DELIMITER = " ";
    private final String input;
    private Command command;
    private String[] params;

    public CommandLine(String input) {
        this.input = input;
        parseCommand(input);
        extractParams(input);
    }

    private void extractParams(String input) {
        if (input == null) {
            params = null;
            return;
        }
        String paramPartOfCommand = removeExtraSpaces(removeCommand(input));
        if (paramPartOfCommand == null) {
            params = null;
            return;
        }
        params = paramPartOfCommand.split(PARAM_DELIMITER);
    }

    private String removeCommand(String input) {
        if (command == Command.ILLEGAL_COMMAND) {
            return input;
        }
        int indexAfterCommand = input.toUpperCase().indexOf(command.name()) + command.name().length();
        String withoutCommand = input.substring(indexAfterCommand, input.length());
        return withoutCommand.trim();
    }

    private void parseCommand(String input) {
        int commandNameIndex = 0;
        try {
            String trimmed = removeExtraSpaces(input);
            if (trimmed == null) {
                command = Command.ILLEGAL_COMMAND;
                return;
            }
            String[] tokens = trimmed.split(PARAM_DELIMITER);
            command = Command.valueOf(tokens[commandNameIndex].toUpperCase());
        } catch (Exception exception) {
            command = Command.ILLEGAL_COMMAND;
        }
    }

    private String removeExtraSpaces(String input) {
        if (input == null) {
            return input;
        }
        String oneOrMoreOccurrences = "+";
        return input.trim().replaceAll(PARAM_DELIMITER + oneOrMoreOccurrences, PARAM_DELIMITER);
    }

    public Command getCommand() {
        return command;
    }

    public String getParameter(int index) {
        if (params == null) {
            return null;
        }
        if (index >= params.length) {
            return null;
        }
        return params[index];
    }

    public int getParameters() {
        return params.length;
    }
}
