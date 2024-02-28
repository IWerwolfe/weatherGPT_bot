package com.app.weatherGPT.bot.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrentWeatherCommand implements IBotCommand {

    @Override
    public String getCommandIdentifier() {
        return "current_weather";
    }

    @Override
    public String getDescription() {
        return "Получить текущий прогноз";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

    }
}