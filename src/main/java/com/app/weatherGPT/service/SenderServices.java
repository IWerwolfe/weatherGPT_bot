package com.app.weatherGPT.service;    /*
 *created by WerWolfe on SenderServices
 */

import com.app.weatherGPT.bot.WeatherBot;
import com.app.weatherGPT.utils.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

@Slf4j
@Component
@RequiredArgsConstructor
public class SenderServices {

    private final TextUtil textUtil;

    public void sendBotMessage(WeatherBot absSender, String message, long chatId, int messageId) {
        sendBotEditMessage(absSender, message, null, chatId, messageId);
    }

    public void sendBotMessage(WeatherBot absSender, String message, Long chatId) {
        sendBotMessage(absSender, message, null, chatId);
    }

    public void sendBotEditMessage(WeatherBot absSender, String message, long chatId, int messageId) {
        sendBotEditMessage(absSender, message, null, chatId, messageId);
    }

    public void sendBotEditMessage(WeatherBot absSender, String message, InlineKeyboardMarkup keyboard, long chatId, int messageId) {

        if (message != null && message.trim().isEmpty()) {
            log.error("Attempt to send an empty message from a user with an ID {}", chatId);
            return;
        }

        BotApiMethod<? extends Serializable> sendMessage = messageId == 0 ?
                createMessage(chatId, message, keyboard) :
                createEditMessage(chatId, message, keyboard, messageId);

        sendBotMessage(absSender, sendMessage);
    }

    public void sendBotMessage(WeatherBot absSender, String message, ReplyKeyboard keyboard, long chatId) {

        if (message != null && message.trim().isEmpty()) {
            log.error("Attempt to send an empty message from a user with an ID {}", chatId);
            return;
        }
        SendMessage sendMessage = createMessage(chatId, message, keyboard);
        sendBotMessage(absSender, sendMessage);
    }

    private EditMessageText createEditMessage(long chatId, String text, InlineKeyboardMarkup keyboard, int messageId) {

        EditMessageText message = new EditMessageText();
        message.setChatId(chatId);
        message.setText(textUtil.shortenText(text, 4095));
        message.setMessageId(messageId);
        message.setReplyMarkup(keyboard);
        return message;
    }

    private SendMessage createMessage(long chatId, String text, ReplyKeyboard keyboard) {

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textUtil.shortenText(text, 4095));
        message.setReplyMarkup(keyboard);
        return message;
    }

    private void sendBotMessage(WeatherBot absSender, BotApiMethod<? extends Serializable> message) {

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

}
