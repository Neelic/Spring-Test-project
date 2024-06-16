package com.github.Neelic.demo.command;

import com.github.Neelic.demo.service.SendBotMessageService;
import com.github.Neelic.demo.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Stop {@link Command}.
 */
public class StopCommand implements Command {

    public static final String STOP_MESSAGE = "Деактивировал все ваши подписки \uD83D\uDE1F.";
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public StopCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        telegramUserService.findByChatId(chatId).ifPresent(
                user -> {
                    user.setActive(false);
                    telegramUserService.save(user);
                }
        );

        sendBotMessageService.sendMessage(chatId, STOP_MESSAGE);
    }
}
