package com.github.Neelic.demo.command;

import com.github.Neelic.demo.javarushclient.dto.GroupStatDTO;
import com.github.Neelic.demo.javarushclient.dto.StatisticDTO;
import com.github.Neelic.demo.service.SendBotMessageService;
import com.github.Neelic.demo.service.StatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static com.github.Neelic.demo.command.AbstractCommandTest.prepareUpdate;
import static com.github.Neelic.demo.command.StatCommand.STAT_MESSAGE;

public class StatCommandTest {

    private SendBotMessageService sendBotMessageService;
    private StatisticsService statisticsService;
    private Command statCommand;

    @BeforeEach
    public void setUp() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        statisticsService = Mockito.mock(StatisticsService.class);
        statCommand = new StatCommand(statisticsService, sendBotMessageService);
    }

    @Test
    public void shouldProperlySendMessage() {
        Long chatId = 1234567L;
        GroupStatDTO groupDto = new GroupStatDTO(1, "group", 1);
        StatisticDTO statisticDTO = new StatisticDTO(1, 1, Collections.singletonList(groupDto), 2.5);
        Mockito.when(statisticsService.countBotStatistic())
                .thenReturn(statisticDTO);

        statCommand.execute(prepareUpdate(chatId, CommandName.STAT.getCommandName()));

        Mockito.verify(sendBotMessageService).sendMessage(chatId, String.format(STAT_MESSAGE,
                statisticDTO.getActiveUserCount(),
                statisticDTO.getInactiveUserCount(),
                statisticDTO.getAverageGroupCountByUser(),
                String.format("%s (id = %s) - %s подписчиков", groupDto.getTitle(),
                        groupDto.getId(), groupDto.getActiveUserCount())));
    }
}