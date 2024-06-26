package com.github.Neelic.demo.command;

import com.github.Neelic.demo.javarushclient.JavaRushGroupClient;
import com.github.Neelic.demo.service.GroupSubService;
import com.github.Neelic.demo.service.SendBotMessageService;
import com.github.Neelic.demo.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;

import static com.github.Neelic.demo.command.CommandName.*;

/**
 * Container of the {@link Command}s, which are using for handling telegram commands.
 */
public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService,
                            JavaRushGroupClient javaRushGroupClient, GroupSubService groupSubService) {
        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(STAT.getCommandName(), new StatCommand(telegramUserService, sendBotMessageService))
                .put(ADD_GROUP_SUB.getCommandName(), new AddGroupSubCommand(
                        sendBotMessageService, javaRushGroupClient, groupSubService))
                .put(DELETE_GROUP_SUB.getCommandName(), new DeleteGroupSubCommand(
                        sendBotMessageService, telegramUserService, groupSubService))
                .put(LIST_GROUP_SUB.getCommandName(), new ListGroupSubCommand(sendBotMessageService, telegramUserService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
