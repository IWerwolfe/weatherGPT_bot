package com.app.weatherGPT.bot.command;

import com.app.weatherGPT.bot.WeatherBot;
import com.app.weatherGPT.dto.Frequency;
import com.app.weatherGPT.model.BotUser;
import com.app.weatherGPT.model.UserCommandCache;
import com.app.weatherGPT.repositories.UserCommandCacheRepository;
import com.app.weatherGPT.service.BotUserServices;
import com.app.weatherGPT.service.ButtonServices;
import com.app.weatherGPT.service.CommandCacheServices;
import com.app.weatherGPT.service.SenderServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionCommand implements IBotCommand {

    private final SenderServices senderServices;
    private final ButtonServices buttonServices;
    private final CommandCacheServices commandCacheServices;

    @Override
    public String getCommandIdentifier() {
        return "subscription";
    }

    @Override
    public String getDescription() {
        return "Подписаться на рассылку прогноза погоды";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        commandCacheServices.clearCommandCaches(message.getFrom());

        String text = "Укажите во сколько вам отправлять прогноз погоды.";
        InlineKeyboardMarkup keyboard = buttonServices.getInlineTime();
        senderServices.sendBotMessage((WeatherBot) absSender, text, keyboard, message.getChatId());
    }

    public void handlerCommandTime(AbsSender absSender, Message message, String arguments) {

        commandCacheServices.addCommandCache(message.getFrom(), getCommandIdentifier(), "time", arguments);

        String text = "Как часто отправлять вам прогноз погоды  ?";
        InlineKeyboardMarkup keyboard = buttonServices.getInlineEnumKeyboard(Frequency.class, "frequency");
        senderServices.sendBotEditMessage((WeatherBot) absSender, text, keyboard, message.getChatId(), message.getMessageId());
    }

    public void handlerCommandFrequency(AbsSender absSender, Message message, String arguments) {

        String text = "Как часто отправлять вам прогноз погоды  ?";
        InlineKeyboardMarkup keyboard = buttonServices.getInlineEnumKeyboard(Frequency.class, "frequency");
        senderServices.sendBotEditMessage((WeatherBot) absSender, text, keyboard, message.getChatId(), message.getMessageId());
    }
}