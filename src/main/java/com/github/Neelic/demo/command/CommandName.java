package com.github.Neelic.demo.command;

/**
 * Enumeration for {@link Command}'s.
 */
public enum CommandName {

    START("/start"),
    HELP("/help"),
    NO("nocommand"),
    STOP("/stop"),
    STAT("/statistics");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
