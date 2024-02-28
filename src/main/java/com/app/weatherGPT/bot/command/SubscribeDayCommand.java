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
public class SubscribeDayCommand implements IBotCommand {

    @Override
    public String getCommandIdentifier() {
        return "subscribe_day";
    }

    @Override
    public String getDescription() {
        return "Подписаться на ежедневную рассылку";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

    }
}