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
public class ForecastDaysCommand implements IBotCommand {

    private final SenderServices senderServices;
    private final WeatherService weatherService;
    private final BotUserServices botUserServices;

    @Override
    public String getCommandIdentifier() {
        return "forecast_days";
    }

    @Override
    public String getDescription() {
        return "Прогноз погоды на 3 дня";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        String desc = weatherService.getDescForecast(botUserServices.getUser(message.getFrom()), 3);
        senderServices.sendBotMessage((WeatherBot) absSender, desc, message.getChatId());
    }
}