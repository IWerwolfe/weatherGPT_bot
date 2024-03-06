package com.app.weatherGPT.service;

import com.app.weatherGPT.model.BotUser;
import com.app.weatherGPT.model.location.UserLocation;
import com.app.weatherGPT.repositories.BotUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

@Slf4j
@Component
@RequiredArgsConstructor
public class BotUserServices {

    private final BotUserRepository userRepository;

    private BotUser user;

    public BotUser getUser(User user) {

        if (this.user == null && user == null) {
            return null;
        }

        return getUser(user.getId());
    }

    public BotUser getUser(long id) {

        if (this.user != null) {
            return this.user;
        }

        user = userRepository
                .findByTelegramId(id)
                .orElse(new BotUser(id));
        user.updateLastActivity();
        
        return user;
    }

    public void updateBotUserStatus(String newStatus, boolean isPrivate, User user) {
        getUser(user);
        updateBotUserStatus(newStatus, isPrivate);
    }

    public void updateBotUserStatus(String newStatus, boolean isPrivate) {

        String text;

        if (user == null) {
            text = String.format("Ошибка при изменении статуса %s, не удалось определить пользователя", newStatus);
        } else {
            user.setIsDeleted(newStatus.equals("kicked") && isPrivate);
            userRepository.save(user);
            text = String.format("У пользователя с id: %s изменился статус на %s", user.getId(), newStatus);
        }
        log.warn(text);
    }

    public void updateLocation(User user, double latitude, double longitude) {
        getUser(user);
        UserLocation location = new UserLocation(latitude, longitude);
        this.user.setLocation(location);
        userRepository.save(this.user);
    }

    public void updateLocation(User user, UserLocation location) {
        getUser(user);
        this.user.setLocation(location);
        userRepository.save(this.user);
    }
}
