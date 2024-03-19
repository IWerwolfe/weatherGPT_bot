package com.app.weatherGPT.bot.command;

import com.app.weatherGPT.bot.WeatherBot;
import com.app.weatherGPT.service.BotUserServices;
import com.app.weatherGPT.service.SenderServices;
import com.app.weatherGPT.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Service
@Slf4j
@RequiredArgsConstructor
public class ForecastTodayCommand implements IBotCommand {

    private final SenderServices senderServices;
    private final WeatherService weatherService;
    private final BotUserServices botUserServices;

    @Override
    public String getCommandIdentifier() {
        return "forecast_today";
    }

    @Override
    public String getDescription() {
        return "Прогноз погоды на сегодня";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        String desc = weatherService.getDescForecast(botUserServices.getUser(message.getFrom()), 1);
        senderServices.sendBotMessage((WeatherBot) absSender, desc, message.getChatId());
    }
}