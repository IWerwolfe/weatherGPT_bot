package com.app.weatherGPT.bot.command;

import com.app.weatherGPT.bot.WeatherBot;
import com.app.weatherGPT.model.BotUser;
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
public class CurrentWeatherCommand implements IBotCommand {

    private final WeatherService weatherService;
    private final SenderServices senderServices;
    private final BotUserServices botUserServices;

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
        BotUser botUser = botUserServices.getUser(message.getFrom());
        String text = weatherService.getDescriptorCurrentWeather(botUser);
        senderServices.sendBotMessage((WeatherBot) absSender, text, message.getChatId());
    }
}