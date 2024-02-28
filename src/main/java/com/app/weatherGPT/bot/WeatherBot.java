package com.app.weatherGPT.bot;

import com.app.weatherGPT.config.Telegram;
import com.app.weatherGPT.model.BotUser;
import com.app.weatherGPT.repositories.BotUserRepository;
import jakarta.persistence.criteria.From;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;


@Service
@Slf4j
public class WeatherBot extends TelegramLongPollingCommandBot {
    private final BotUserRepository botUserRepository;

    private final String botUsername;
    private final Telegram telegram;
    private final Sender sender;

    public WeatherBot(
            List<IBotCommand> commandList,
            Telegram telegram, Sender sender,
            BotUserRepository botUserRepository) {

        super(telegram.getBot().getToken());
        this.telegram = telegram;
        this.botUsername = telegram.getBot().getUsername();
        this.sender = sender;

        commandList.forEach(this::register);
        this.botUserRepository = botUserRepository;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        if (update.hasMyChatMember()) {
            handlerMyChatMember(update);
            return;
        }

        String text = """
                Я не умею общаться в свободном стиле, лучше отправь мне команду из этого списка:
                                
                /get_weather - получить текущий прогноз
                /subscribe_day - подписаться на ежедневную рассылку
                /subscribe_week - подписаться на еженедельную рассылку
                /get_subscription - информация о подписке
                /unsubscribe - отменить подписку
                """;

        Long userId = update.getMessage().getChatId();
        sender.sendBotMessage(this, text, userId);
    }

    private void handlerMyChatMember(Update update) {

        User user = update.getMyChatMember().getFrom();
        Boolean isPrivate = update.getMyChatMember().getChat().isUserChat();

        BotUser botUser = getBotUser(user);
        String newStatus = update.getMyChatMember().getNewChatMember().getStatus();

        botUser.setIsValid(!newStatus.equals("kicked") && isPrivate);
        botUserRepository.save(botUser);

        String text = String.format("У пользователя с id: %s изменился статус на %s", user.getId(), newStatus);
        log.warn(text);
    }

    private BotUser getBotUser(User user) {
        return botUserRepository
                .findByTelegramId(user.getId())
                .orElse(new BotUser(user));
    }
}
