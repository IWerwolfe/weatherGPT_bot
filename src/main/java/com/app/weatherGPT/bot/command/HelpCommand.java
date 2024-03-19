package com.app.weatherGPT.bot.command;

import com.app.weatherGPT.bot.WeatherBot;
import com.app.weatherGPT.service.SenderServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HelpCommand implements IBotCommand {

    private final SenderServices senderServices;
    private final String commandPattern = "/%s - %s";

    private final List<IBotCommand> commandList;

    @Override
    public String getCommandIdentifier() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Информация о командах";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        String text = """
                Данный бот предназначен для получения информации о погоде в вашем городе.
                                
                Поддерживается разные режимы работы:
                1. Стандартный режим - вам отправляется обычный прогноз погоды.
                2. Творческий режим - прогноз погоды формируется нейросетью.
                3. Нецензурный режим - прогноз погоды содержит нецензурные слова.
                
                Доступные команды:
                """ + getDescCommands();

        senderServices.sendBotMessage((WeatherBot) absSender, text, message.getChatId());
    }

    private String getDescCommands() {

        StringBuilder builder = new StringBuilder();
        commandList.forEach(iBotCommand -> {
            String text = String.format(commandPattern, iBotCommand.getCommandIdentifier(), iBotCommand.getDescription());
            builder.append(System.lineSeparator()).append(text);
        });

        return builder.toString();
    }
}