package com.github.Neelic.demo.service;

import com.github.Neelic.demo.repository.TelegramUserRepository;
import com.github.Neelic.demo.repository.entity.TelegramUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link TelegramUserService}.
 */
@Service
public class TelegramUserServiceImpl implements TelegramUserService {

    private final TelegramUserRepository telegramUserRepository;

    public TelegramUserServiceImpl(TelegramUserRepository telegramUserRepository) {
        this.telegramUserRepository = telegramUserRepository;
    }

    @Override
    public void save(TelegramUser telegramUser) {
        telegramUserRepository.save(telegramUser);
    }

    @Override
    public List<TelegramUser> findAllActiveUsers() {
        return telegramUserRepository.findAllByActiveTrue();
    }

    @Override
    public Optional<TelegramUser> findByChatId(String chatId) {
        return telegramUserRepository.findById(chatId);
    }

    @Override
    public List<TelegramUser> findAllInActiveUsers() {
        return telegramUserRepository.findAllByActiveFalse();
    }
}
