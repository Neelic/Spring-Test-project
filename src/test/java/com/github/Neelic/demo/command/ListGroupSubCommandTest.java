package com.github.Neelic.demo.command;

import com.github.Neelic.demo.repository.entity.GroupSub;
import com.github.Neelic.demo.repository.entity.TelegramUser;
import com.github.Neelic.demo.service.SendBotMessageService;
import com.github.Neelic.demo.service.TelegramUserService;
import org.junit.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.Neelic.demo.command.CommandName.LIST_GROUP_SUB;

public class ListGroupSubCommandTest {

    @Test
    public void shouldProperlyShowsListGroupSub() {
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setActive(true);
        telegramUser.setChatId(1L);

        List<GroupSub> groupSubList = new ArrayList<>();
        groupSubList.add(populateGroupSub(1, "gs1"));
        groupSubList.add(populateGroupSub(2, "gs2"));
        groupSubList.add(populateGroupSub(3, "gs3"));
        groupSubList.add(populateGroupSub(4, "gs4"));

        telegramUser.setGroupSubs(groupSubList);

        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

        Mockito.when(telegramUserService.findByChatId(telegramUser.getChatId())).thenReturn(Optional.of(telegramUser));

        ListGroupSubCommand command = new ListGroupSubCommand(sendBotMessageService, telegramUserService);

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(telegramUser.getChatId());
        Mockito.when(message.getText()).thenReturn(LIST_GROUP_SUB.getCommandName());
        update.setMessage(message);

        String collectedGroups = "Я нашел все подписки на группы: \n\n" +
                telegramUser.getGroupSubs().stream()
                        .map(it -> "Группа: " + it.getTitle() + " ID - " + it.getId() + "\n")
                        .collect(Collectors.joining());

        command.execute(update);

        Mockito.verify(sendBotMessageService).sendMessage(telegramUser.getChatId(), collectedGroups);
    }

    private GroupSub populateGroupSub(Integer id, String title) {
        GroupSub gs = new GroupSub();
        gs.setId(id);
        gs.setTitle(title);
        return gs;
    }
}