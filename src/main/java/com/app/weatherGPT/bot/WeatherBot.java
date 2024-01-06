package com.app.weatherGPT.bot;

import com.app.weatherGPT.config.Telegram;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;


@Service
@Slf4j
public class WeatherBot extends TelegramLongPollingCommandBot {

    private final String botUsername;
    private final Telegram telegram;

    public WeatherBot(
            List<IBotCommand> commandList,
            Telegram telegram) {

        super(telegram.getBot().getToken());
        this.telegram = telegram;
        this.botUsername = telegram.getBot().getUsername();

        commandList.forEach(this::register);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        String text = """
                Я не умею общаться в свободном стиле, лучше отправь мне команду из этого списка:
                                
                /get_weather - получить текущий прогноз
                /subscribe_day - подписаться на ежедневную рассылку
                /subscribe_week - подписаться на еженедельную рассылку
                /get_subscription - информация о подписке
                /unsubscribe - отменить подписку
                """;

        Long userId = update.getMessage().getChatId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(userId);

        Sender sender = new Sender();
        sender.sendMessage(this, sendMessage, "nonCommand");
    }
}
