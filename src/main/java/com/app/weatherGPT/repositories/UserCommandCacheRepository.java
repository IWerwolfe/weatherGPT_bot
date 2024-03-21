package com.app.weatherGPT.repositories;

import com.app.weatherGPT.model.BotUser;
import com.app.weatherGPT.model.UserCommandCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserCommandCacheRepository extends JpaRepository<UserCommandCache, Long> {
    @Query("select u from UserCommandCache u where u.user = ?1")
    List<UserCommandCache> findByUser(BotUser user);
}