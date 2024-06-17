package com.github.Neelic.demo.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Telegram user entity.
 */
@Data
@Entity
@Table(name = "tg_user")
public class TelegramUser {

    @Id
    @Column(name = "chat_id", nullable = false)
    private String chatId;

    @Column(name = "active")
    private boolean active;
}
