package com.github.Neelic.demo.command;

import com.github.Neelic.demo.bot.TelegramBot;
import com.github.Neelic.demo.service.SendBotMessageService;
import com.github.Neelic.demo.service.SendBotMessageServiceImpl;
import com.github.Neelic.demo.service.TelegramUserService;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.testng.annotations.Test;

public abstract class AbstractCommandTest {

    protected TelegramBot bot = Mockito.mock(TelegramBot.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(bot);
    protected TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

    abstract String getCommandName();

    abstract String getCommandMessage();

    abstract Command getCommand();

    @Test
    public void shouldProperlyExecuteCommand() {
        Long chatId = 1234567824356L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(getCommandMessage());
        sendMessage.enableHtml(true);

        getCommand().execute(update);

        try {
            Mockito.verify(bot).execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
