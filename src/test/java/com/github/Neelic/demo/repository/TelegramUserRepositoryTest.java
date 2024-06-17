package com.github.Neelic.demo.repository;

import com.github.Neelic.demo.repository.entity.TelegramUser;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * Integration-level testing for {@link TelegramUserRepository}.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TelegramUserRepositoryTest {

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    @Test
    @Sql(scripts = {"/sql/clear_db.sql", "/sql/telegram_users.sql"})
    public void shouldProperlyFindAllActiveUsers() {
        List<TelegramUser> users = telegramUserRepository.findAllByActiveTrue();

        Assertions.assertEquals(5, users.size());
    }

    @Test
    @Sql(scripts = {"/sql/clear_db.sql"})
    public void shouldProperlySaveTelegramUser() {
        //given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId("1234567890");
        telegramUser.setActive(false);
        telegramUserRepository.save(telegramUser);

        //when
        Optional<TelegramUser> saved = telegramUserRepository.findById(telegramUser.getChatId());

        //then
        Assertions.assertTrue(saved.isPresent());
        Assertions.assertEquals(telegramUser, saved.get());
    }
}


