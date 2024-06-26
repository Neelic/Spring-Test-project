package com.github.Neelic.demo.command;

import static com.github.Neelic.demo.command.CommandName.START;
import static com.github.Neelic.demo.command.StartCommand.START_MESSAGE;

public class StartCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return START.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return START_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StartCommand(sendBotMessageService, telegramUserService);
    }
}
