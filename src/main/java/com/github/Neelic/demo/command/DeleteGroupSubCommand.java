package com.github.Neelic.demo.command;

import com.github.Neelic.demo.repository.entity.GroupSub;
import com.github.Neelic.demo.repository.entity.TelegramUser;
import com.github.Neelic.demo.service.GroupSubService;
import com.github.Neelic.demo.service.SendBotMessageService;
import com.github.Neelic.demo.service.TelegramUserService;
import jakarta.ws.rs.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.Neelic.demo.command.CommandName.ADD_GROUP_SUB;
import static com.github.Neelic.demo.command.CommandName.DELETE_GROUP_SUB;
import static org.apache.commons.lang3.StringUtils.SPACE;

public class DeleteGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final GroupSubService groupSubService;

    public DeleteGroupSubCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService,
                                 GroupSubService groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.groupSubService = groupSubService;
    }

    @Override
    public void execute(Update update) {
        if (CommandUtils.getMessage(update).equalsIgnoreCase(DELETE_GROUP_SUB.getCommandName())) {
            sendGroupIdList(CommandUtils.getChatId(update));
            return;
        }

        String groupId = CommandUtils.getMessage(update).split(SPACE)[1];
        Long chatId = CommandUtils.getChatId(update);

        if (StringUtils.isNumeric(groupId)) {
            Optional<GroupSub> optionalGroupSub = groupSubService.findById(Integer.valueOf(groupId));

            if (optionalGroupSub.isPresent()) {
                GroupSub groupSub = optionalGroupSub.get();
                TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
                groupSub.getUsers().remove(telegramUser);
                groupSubService.save(groupSub);
                sendBotMessageService.sendMessage(chatId, "Удалил подписку на группу: " + groupSub.getTitle());
            } else {
                sendBotMessageService.sendMessage(chatId, "Не нашел такой группы. Попробуй еще раз.");
            }
        } else {
            sendBotMessageService.sendMessage(chatId, "неправильный формат ID группы.\n " +
                    "ID должно быть целым положительным числом");
        }
    }

    private void sendGroupIdList(Long chatId) {
        String message;
        List<GroupSub> groupSubs = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new)
                .getGroupSubs();

        if (CollectionUtils.isEmpty(groupSubs)) {
            message = "Пока нет подписок на группы. Чтобы добавить подписку напиши " + ADD_GROUP_SUB.getCommandName();
        } else {
            message = "Чтобы удалить подписку на группу - передай комадну вместе с ID группы. \n" +
                    "Например: " + DELETE_GROUP_SUB.getCommandName() + " 16 \n\n" +
                    "я подготовил список всех групп, на которые ты подписан) \n\n" +
                    "имя группы - ID группы \n\n" +
                    groupSubs.stream()
                            .map(group -> String.format("%s - %s \n", group.getTitle(), group.getId()))
                            .collect(Collectors.joining());
        }

        sendBotMessageService.sendMessage(chatId, message);
    }
}
