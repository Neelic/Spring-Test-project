package com.github.Neelic.demo.command;

import com.github.Neelic.demo.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.Neelic.demo.command.CommandName.*;

/**
 * Help {@link Command}.
 */
public class HelpCommand implements Command {
    public final static String HELP_MESSAGE = String.format("""
                     ✨<b>Дотупные команды</b>✨

                     <b>Начать\\закончить работу с ботом</b>
                     %s - начать работу со мной
                     %s - приостановить работу со мной
                     \s
                     Работа с подписками на группы
                     %s - подписаться на группу статей
                     %s - получить список групп, на которые подписан
                     \s
                     %s - получить статистику пользователей
                     %s - получить помощь в работе со мной
                    \s""",
            START.getCommandName(), STOP.getCommandName(), ADD_GROUP_SUB.getCommandName(), LIST_GROUP_SUB.getCommandName(),
            STAT.getCommandName(), HELP.getCommandName());
    private final SendBotMessageService sendBotMessageService;

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
