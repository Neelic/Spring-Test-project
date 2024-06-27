package com.github.Neelic.demo.service;

import com.github.Neelic.demo.javarushclient.JavaRushPostClient;
import com.github.Neelic.demo.javarushclient.dto.PostInfo;
import com.github.Neelic.demo.repository.entity.GroupSub;
import com.github.Neelic.demo.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class FindNewArticleServiceImpl implements FindNewArticleService {

    public static final String JAVARUSH_WEB_POST_FORMAT = "https://javarush.com/groups/posts/%s";

    private final GroupSubService groupSubService;
    private final JavaRushPostClient javaRushPostClient;
    private final SendBotMessageService sendMessageService;

    @Autowired
    public FindNewArticleServiceImpl(GroupSubService groupSubService, JavaRushPostClient javaRushPostClient,
                                     SendBotMessageService sendMessageService) {
        this.groupSubService = groupSubService;
        this.javaRushPostClient = javaRushPostClient;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void findNewArticles() {
        groupSubService.findAll().forEach(gSub -> {
            List<PostInfo> newPosts = javaRushPostClient.findNewPosts(gSub.getId(), gSub.getLastArticleId());

            setNewLastArticleId(gSub, newPosts);

            notifySubscribersAboutNewArticles(gSub, newPosts);
        });
    }

    private void notifySubscribersAboutNewArticles(GroupSub gSub, List<PostInfo> newPosts) {
        Collections.reverse(newPosts);
        List<String> messagesWithNewArticles = newPosts.stream()
                .map(post -> String.format("""
                                ✨Вышла новая статья <b>%s</b> в группе <b>%s</b>.✨

                                <b>Описание:</b> %s

                                <b>Ссылка:</b> %s
                                """,
                        post.getTitle(), gSub.getTitle(), post.getDescription(), getPostUrl(post.getKey())))
                .toList();

        gSub.getUsers().stream()
                .filter(TelegramUser::isActive)
                .forEach(it -> sendMessageService.sendMessage(it.getChatId(), String.join("", messagesWithNewArticles)));
    }

    private void setNewLastArticleId(GroupSub gSub, List<PostInfo> newPosts) {
        newPosts.stream().mapToInt(PostInfo::getId).max()
                .ifPresent(id -> {
                    gSub.setLastArticleId(id);
                    groupSubService.save(gSub);
                });
    }

    private String getPostUrl(String key) {
        return String.format(JAVARUSH_WEB_POST_FORMAT, key);
    }
}
