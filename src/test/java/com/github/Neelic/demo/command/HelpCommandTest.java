package com.github.Neelic.demo.command;

import static com.github.Neelic.demo.command.CommandName.HELP;
import static com.github.Neelic.demo.command.HelpCommand.HELP_MESSAGE;

public class HelpCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return HELP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return HELP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new HelpCommand(sendBotMessageService);
    }
}
