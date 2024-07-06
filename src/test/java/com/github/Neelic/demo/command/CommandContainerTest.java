package com.github.Neelic.demo.command;

import com.github.Neelic.demo.javarushclient.JavaRushGroupClient;
import com.github.Neelic.demo.javarushclient.JavaRushGroupClientImpl;
import com.github.Neelic.demo.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

@DisplayName("Unit-level testing for CommandContainer")
class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    void setUp() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        JavaRushGroupClient javaRushGroupClient = Mockito.mock(JavaRushGroupClientImpl.class);
        GroupSubService groupSubService = Mockito.mock(GroupSubServiceImpl.class);
        StatisticsService statisticsService = Mockito.mock(StatisticsServiceImpl.class);
        commandContainer = new CommandContainer(sendBotMessageService, telegramUserService, javaRushGroupClient,
                groupSubService, List.of("admin1"), statisticsService);
    }

    @Test
    public void shouldGetAllTheExistingCommands() {
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.findCommand(commandName.getCommandName(), "admin1");
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }

    @Test
    public void shouldReturnUnknownCommand() {
        String unknownCommand = "/fgjhdfgdfg";

        Command command = commandContainer.findCommand(unknownCommand, "admin1");

        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }
}