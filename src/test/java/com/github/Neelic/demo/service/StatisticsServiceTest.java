package com.github.Neelic.demo.service;

import com.github.Neelic.demo.javarushclient.dto.GroupStatDTO;
import com.github.Neelic.demo.javarushclient.dto.StatisticDTO;
import com.github.Neelic.demo.repository.entity.GroupSub;
import com.github.Neelic.demo.repository.entity.TelegramUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import static java.util.Collections.singletonList;

public class StatisticsServiceTest {

    private GroupSubService groupSubService;
    private TelegramUserService telegramUserService;

    private StatisticsService statisticsService;

    @Before
    public void setUp() {
        groupSubService = Mockito.mock(GroupSubService.class);
        telegramUserService = Mockito.mock(TelegramUserService.class);
        statisticsService = new StatisticsServiceImpl(groupSubService, telegramUserService);
    }

    @Test
    public void shouldProperlySendStatDTO() {
        Mockito.when(telegramUserService.findAllInActiveUsers()).thenReturn(singletonList(new TelegramUser()));
        TelegramUser activeUser = new TelegramUser();
        activeUser.setGroupSubs(singletonList(new GroupSub()));
        Mockito.when(telegramUserService.findAllActiveUsers()).thenReturn(singletonList(activeUser));
        GroupSub groupSub = new GroupSub();
        groupSub.setTitle("group");
        groupSub.setId(1);
        groupSub.setUsers(singletonList(new TelegramUser()));
        Mockito.when(groupSubService.findAll()).thenReturn(singletonList(groupSub));

        StatisticDTO statisticDTO = statisticsService.countBotStatistic();

        Assertions.assertNotNull(statisticDTO);
        Assertions.assertEquals(1, statisticDTO.getActiveUserCount());
        Assertions.assertEquals(1, statisticDTO.getInactiveUserCount());
        Assertions.assertEquals(1.0, statisticDTO.getAverageGroupCountByUser());
        Assertions.assertEquals(singletonList(new GroupStatDTO(groupSub.getId(), groupSub.getTitle(), groupSub.getUsers().size())),
                statisticDTO.getGroupStatDTOs());
    }
}