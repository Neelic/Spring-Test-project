package com.github.Neelic.demo.command;

import com.github.Neelic.demo.javarushclient.JavaRushGroupClient;
import com.github.Neelic.demo.javarushclient.dto.GroupDiscussionInfo;
import com.github.Neelic.demo.javarushclient.dto.GroupRequestArgs;
import com.github.Neelic.demo.repository.entity.GroupSub;
import com.github.Neelic.demo.service.GroupSubService;
import com.github.Neelic.demo.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static com.github.Neelic.demo.command.CommandName.ADD_GROUP_SUB;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class AddGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final JavaRushGroupClient javaRushGroupClient;
    private final GroupSubService groupSubService;

    public AddGroupSubCommand(SendBotMessageService sendBotMessageService, JavaRushGroupClient javaRushGroupClient,
                              GroupSubService groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.javaRushGroupClient = javaRushGroupClient;
        this.groupSubService = groupSubService;
    }

    @Override
    public void execute(Update update) {
        if (CommandUtils.getMessage(update).equalsIgnoreCase(ADD_GROUP_SUB.getCommandName())) {
            sendGroupIdList(String.valueOf(CommandUtils.getChatId(update)));
            return;
        }

        String groupId = CommandUtils.getMessage(update).split(SPACE)[1];
        String chatId = String.valueOf(CommandUtils.getChatId(update));
        if (isNumeric(groupId)) {
            GroupDiscussionInfo groupById = javaRushGroupClient.getGroupById(Integer.parseInt(groupId));
            if (groupById.getId() == null) {
                sendGroupNotFound(chatId, groupId);
            }
            GroupSub savedGroupSub = groupSubService.save(chatId, groupById);
            sendBotMessageService.sendMessage(chatId, "Подписал на группу - " + savedGroupSub.getTitle());
        } else {
            sendGroupNotFound(chatId, groupId);
        }
    }

    private void sendGroupIdList(String chatId) {
        String groupIds = javaRushGroupClient.getGroupList(GroupRequestArgs.builder().build()).stream()
                .map(group -> String.format("%s - %s \n", group.getTitle(), group.getId()))
                .collect(Collectors.joining());
        String message = """
                Чтобы подписаться на группу - передай комадну вместе с ID группы.\s
                Например: /addGroupSub 16.\s

                я подготовил список всех групп - выберай какую хочешь :)\s

                имя группы - ID группы\s

                %s""";
        sendBotMessageService.sendMessage(chatId, String.format(message, groupIds));
    }

    private void sendGroupNotFound(String chatId, String groupId) {
        String groupNotFoundMessage = "Нет группы с ID = \"%s\"";
        sendBotMessageService.sendMessage(chatId, String.format(groupNotFoundMessage, groupId));
    }
}
