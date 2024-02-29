package com.app.weatherGPT.bot.command;

import com.app.weatherGPT.bot.WeatherBot;
import com.app.weatherGPT.service.ButtonServices;
import com.app.weatherGPT.service.SenderServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Service
@Slf4j
@RequiredArgsConstructor
public class StartCommand implements IBotCommand {

    private final SenderServices senderServices;
    private final ButtonServices buttonServices;

    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Перезапустить бота / обновить данные";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        String text = message.getFrom().getUserName() + """
                , добро пожаловать в бот "WeatherGPT!
                
                Данный бот предназначен для получения информации о погоде в вашем городе.
                
                Поддерживается разные режимы работы:
                1. Стандартный режим - вам отправляется обычный прогноз погоды.
                2. Творческий режим - прогноз погоды формируется нейросетью.
                3. Нецензурный режим - прогноз погоды содержит нецензурные слова.
                
                Для начала работы вам нужно выбрать город.
                Для этого нажмите на кнопку "Мое местоположение" или введите название города
                """;

        ReplyKeyboard keyboard = buttonServices.keyboardMarkupGetLocation();
        senderServices.sendBotMessage((WeatherBot) absSender, text, keyboard, message.getChatId());
    }
}