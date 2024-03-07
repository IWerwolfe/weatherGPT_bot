package com.app.weatherGPT.service;

import com.app.weatherGPT.dto.Lang;
import com.app.weatherGPT.model.BotUser;
import com.app.weatherGPT.model.location.City;
import com.app.weatherGPT.model.location.UserLocation;
import com.app.weatherGPT.repositories.BotUserRepository;
import com.app.weatherGPT.utils.ConverterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    public void updateUser(User user) {

        if (this.user == null && user == null) {
            log.error("Ошибка при обновлении пользователя, данные для обновления не корректны");
            return;
        }

        getUser(user.getId());
        BeanUtils.copyProperties(user, this.user, "id");
        this.user.setLanguage_code(ConverterUtil.convertToEnum(user.getLanguageCode().toUpperCase(), Lang.class));
        userRepository.save(this.user);
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

    public void updateLocation(User user, City city) {
        UserLocation location = new UserLocation(city);
        updateLocation(user, location);
    }
}
