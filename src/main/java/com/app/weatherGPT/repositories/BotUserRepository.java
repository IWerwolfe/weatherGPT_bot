package com.app.weatherGPT.repositories;

import com.app.weatherGPT.model.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BotUserRepository extends JpaRepository<BotUser, Long> {
    @Query("select b from BotUser b where b.telegramId = ?1")
    Optional<BotUser> findByTelegramId(Long telegramId);
}