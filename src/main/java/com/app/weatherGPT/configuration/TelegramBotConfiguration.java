package com.app.weatherGPT.configuration;


import com.app.weatherGPT.bot.WeatherBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TelegramBotConfiguration {

    @Bean
    TelegramBotsApi telegramBotsApi(WeatherBot weatherBot) {
        TelegramBotsApi botsApi = null;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(weatherBot);
            log.info("Telegram bot {} launched", "");
        } catch (TelegramApiException e) {
            log.error("Error occurred while sending message to telegram!", e);
        }
        return botsApi;
    }
}
