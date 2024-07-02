package com.github.Neelic.demo.command;

import com.github.Neelic.demo.javarushclient.JavaRushGroupClient;
import com.github.Neelic.demo.service.GroupSubService;
import com.github.Neelic.demo.service.SendBotMessageService;
import com.github.Neelic.demo.service.StatisticsService;
import com.github.Neelic.demo.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Objects;

import static com.github.Neelic.demo.command.CommandName.*;

/**
 * Container of the {@link Command}s, which are using for handling telegram commands.
 */
public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;
    private final List<String> admins;

    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService,
                            JavaRushGroupClient javaRushGroupClient, GroupSubService groupSubService, List<String> admins,
                            StatisticsService statisticsService) {
        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(STAT.getCommandName(), new StatCommand(statisticsService, sendBotMessageService))
                .put(ADD_GROUP_SUB.getCommandName(), new AddGroupSubCommand(
                        sendBotMessageService, javaRushGroupClient, groupSubService))
                .put(DELETE_GROUP_SUB.getCommandName(), new DeleteGroupSubCommand(
                        sendBotMessageService, telegramUserService, groupSubService))
                .put(LIST_GROUP_SUB.getCommandName(), new ListGroupSubCommand(sendBotMessageService, telegramUserService))
                .put(ADMIN_HELP.getCommandName(), new AdminHelpCommand(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
        this.admins = admins;
    }

    public Command findCommand(String commandIdentifier, String username) {
        Command orDefault = commandMap.getOrDefault(commandIdentifier, unknownCommand);

        if (isAdminCommand(orDefault)) {
            if (admins.contains(username)) {
                return orDefault;
            } else {
                return unknownCommand;
            }
        }

        return orDefault;
    }

    private boolean isAdminCommand(Command command) {
        return Objects.nonNull(command.getClass().getAnnotation(AdminCommand.class));
    }
}
