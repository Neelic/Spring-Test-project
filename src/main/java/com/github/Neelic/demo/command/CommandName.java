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
    ADMIN_HELP("/ahelp"),
    NO("nocommand"),
    ADD_GROUP_SUB("/addGroupSub"),
    LIST_GROUP_SUB("/listGroupSub"),
    DELETE_GROUP_SUB("/deleteGroupSub"),
    STAT("/statistics");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

}
