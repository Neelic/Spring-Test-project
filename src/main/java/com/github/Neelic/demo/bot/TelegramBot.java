package com.github.Neelic.demo.bot;

import com.github.Neelic.demo.command.CommandContainer;
import com.github.Neelic.demo.javarushclient.JavaRushGroupClient;
import com.github.Neelic.demo.service.GroupSubService;
import com.github.Neelic.demo.service.SendBotMessageServiceImpl;
import com.github.Neelic.demo.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.Neelic.demo.command.CommandName.NO;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";
    private final CommandContainer commandContainer;
    @Value("${bot.token}")
    private String token;
    @Value("${bot.username}")
    private String botUsername;

    @Autowired
    public TelegramBot(TelegramUserService telegramUserService, JavaRushGroupClient groupClient, GroupSubService groupSubService) {
        this.commandContainer =
                new CommandContainer(new SendBotMessageServiceImpl(this), telegramUserService, groupClient, groupSubService);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();

            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].substring(1).toLowerCase();
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
