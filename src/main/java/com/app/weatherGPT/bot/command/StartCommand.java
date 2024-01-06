package com.app.weatherGPT.bot.command;

import com.app.weatherGPT.bot.Sender;
import com.app.weatherGPT.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;


/**
 * Обработка команды начала работы с ботом
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class StartCommand implements IBotCommand {

    private final WeatherService weatherService;
    private final Sender sender;

    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Запускает бота";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        String text = weatherService.getDescriptorCurrentWeather();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(text);

        sender.sendMessage(absSender, sendMessage, getCommandIdentifier());
    }

}