package com.github.Neelic.demo.service;

import com.github.Neelic.demo.repository.entity.TelegramUser;

import java.util.List;
import java.util.Optional;

/**
 * {@link org.springframework.stereotype.Service} for handling {@link TelegramUser} entity.
 */
public interface TelegramUserService {

    /**
     * Save provided {@link TelegramUser} entity.
     *
     * @param telegramUser provided telegram user.
     */
    void save(TelegramUser telegramUser);

    /**
     * Retrieve all active {@link TelegramUser}.
     *
     * @return the collection of the active {@link TelegramUser} objects.
     */
    List<TelegramUser> retrieveAllActiveUsers();

    /**
     * Find {@link TelegramUser} by chatId.
     *
     * @param chatId provided Chat ID
     * @return {@link TelegramUser} with provided chat ID or null otherwise.
     */
    Optional<TelegramUser> findByChatId(String chatId);
}
