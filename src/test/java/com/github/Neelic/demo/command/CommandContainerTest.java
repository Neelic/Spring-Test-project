package com.github.Neelic.demo.command;

import com.github.Neelic.demo.javarushclient.JavaRushGroupClient;
import com.github.Neelic.demo.javarushclient.JavaRushGroupClientImpl;
import com.github.Neelic.demo.service.GroupSubService;
import com.github.Neelic.demo.service.GroupSubServiceImpl;
import com.github.Neelic.demo.service.SendBotMessageService;
import com.github.Neelic.demo.service.TelegramUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

@DisplayName("Unit-level testing for CommandContainer")
class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    void setUp() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        JavaRushGroupClient javaRushGroupClient = Mockito.mock(JavaRushGroupClientImpl.class);
        GroupSubService groupSubService = Mockito.mock(GroupSubServiceImpl.class);
        commandContainer = new CommandContainer(sendBotMessageService, telegramUserService, javaRushGroupClient,
                groupSubService);
    }

    @Test
    public void shouldGetAllTheExistingCommands() {
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.retrieveCommand(commandName.getCommandName());
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }

    @Test
    public void shouldReturnUnknownCommand() {
        String unknownCommand = "/fgjhdfgdfg";

        Command command = commandContainer.retrieveCommand(unknownCommand);

        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }
}