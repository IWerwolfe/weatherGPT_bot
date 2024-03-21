package com.app.weatherGPT.repositories;

import com.app.weatherGPT.model.BotUser;
import com.app.weatherGPT.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("select s from Subscription s where s.user = ?1")
    List<Subscription> findByUser(BotUser user);
}