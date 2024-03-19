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
public class SubscriptionCommand implements IBotCommand {

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

    }
}