package com.github.Neelic.demo.command;

import com.github.Neelic.demo.service.SendBotMessageService;
import com.github.Neelic.demo.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StatCommand implements Command {

    public final static String STAT_MESSAGE = "Telegram бот использует %s человек.";
    private final TelegramUserService telegramUserService;
    private final SendBotMessageService sendBotMessageService;

    @Autowired
    public StatCommand(TelegramUserService telegramUserService, SendBotMessageService sendBotMessageService) {
        this.telegramUserService = telegramUserService;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        int activeUsersCount = telegramUserService.retrieveAllActiveUsers().size();
        sendBotMessageService.sendMessage(
                update.getMessage().getChatId().toString(),
                String.format(STAT_MESSAGE, activeUsersCount)
        );
    }
}
