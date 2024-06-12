package com.github.Neelic.demo.command;

import static com.github.Neelic.demo.command.CommandName.NO;
import static com.github.Neelic.demo.command.NoCommand.NO_MESSAGE;

public class NoCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return NO.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return NO_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new NoCommand(sendBotMessageService);
    }
}
