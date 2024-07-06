package com.github.Neelic.demo.command;

import com.github.Neelic.demo.javarushclient.dto.StatisticDTO;
import com.github.Neelic.demo.service.SendBotMessageService;
import com.github.Neelic.demo.service.StatisticsService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

@AdminCommand
public class StatCommand implements Command {

    public final static String STAT_MESSAGE = """
            <b>Подготовил статистику</b>✨\\n
            - Количество активных пользователей: %s\\n
            - Количество неактивных пользователей: %s\\n
            - Среднее количество групп на одного пользователя: %s\\n\\n
            <b>Информация по активным группам</b>:\\n
            %s""";
    private final StatisticsService statisticsService;
    private final SendBotMessageService sendBotMessageService;

    public StatCommand(StatisticsService statisticsService, SendBotMessageService sendBotMessageService) {
        this.statisticsService = statisticsService;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        StatisticDTO statisticDTO = statisticsService.countBotStatistic();

        String collectedGroups = statisticDTO.getGroupStatDTOs().stream()
                .map(it -> String.format("%s (id = %s) - %s подписчиков", it.getTitle(), it.getId(), it.getActiveUserCount()))
                .collect(Collectors.joining("\n"));

        sendBotMessageService.sendMessage(update.getMessage().getChatId(), String.format(STAT_MESSAGE,
                statisticDTO.getActiveUserCount(),
                statisticDTO.getInactiveUserCount(),
                statisticDTO.getAverageGroupCountByUser(),
                collectedGroups));
    }
}
