package com.github.Neelic.demo.service;

import com.github.Neelic.demo.javarushclient.dto.GroupDiscussionInfo;
import com.github.Neelic.demo.repository.GroupSubRepository;
import com.github.Neelic.demo.repository.entity.GroupSub;
import com.github.Neelic.demo.repository.entity.TelegramUser;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupSubServiceImpl implements GroupSubService {

    private final GroupSubRepository groupSubRepository;
    private final TelegramUserService telegramUserService;

    @Autowired
    public GroupSubServiceImpl(GroupSubRepository groupSubRepository, TelegramUserService telegramUserService) {
        this.groupSubRepository = groupSubRepository;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo) {
        TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);

        GroupSub groupSub;
        Optional<GroupSub> groupSubFromDB = groupSubRepository.findById(groupDiscussionInfo.getId());

        if (groupSubFromDB.isPresent()) {
            groupSub = groupSubFromDB.get();
            Optional<TelegramUser> first = groupSub.getUsers().stream()
                    .filter(t -> t.getChatId().equalsIgnoreCase(chatId))
                    .findFirst();

            if (first.isEmpty()) {
                groupSub.addUser(telegramUser);
            }
        } else {
            groupSub = new GroupSub();
            groupSub.addUser(telegramUser);
            groupSub.setId(groupDiscussionInfo.getId());
            groupSub.setTitle(groupDiscussionInfo.getTitle());
        }

        return groupSubRepository.save(groupSub);
    }
}
