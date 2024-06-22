package com.github.Neelic.demo.command;

import lombok.Getter;

/**
 * Enumeration for {@link Command}'s.
 */
@Getter
public enum CommandName {

    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    NO("nocommand"),
    ADD_GROUP_SUB("/addGroupSub"),
    LIST_GROUP_SUB("/listGroupSub"),
    STAT("/statistics");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

}
