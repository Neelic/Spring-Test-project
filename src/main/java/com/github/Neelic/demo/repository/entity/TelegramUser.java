package com.github.Neelic.demo.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<GroupSub> groupSubs;
}
