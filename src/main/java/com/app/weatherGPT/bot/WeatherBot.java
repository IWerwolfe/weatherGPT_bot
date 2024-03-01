package com.app.weatherGPT.bot;

import com.app.weatherGPT.config.Telegram;
import com.app.weatherGPT.model.BotUser;
import com.app.weatherGPT.service.BotUserServices;
import com.app.weatherGPT.service.SenderServices;
import com.app.weatherGPT.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.*;

import java.util.Collection;
import java.util.List;


@Service
@Slf4j
public class WeatherBot extends TelegramLongPollingCommandBot {

    private final String botUsername;
    private final Telegram telegram;
    private final SenderServices senderServices;
    private final BotUserServices botUserServices;
    private final WeatherService weatherService;

    private BotUser botUser;

    public WeatherBot(
            List<IBotCommand> commandList,
            Telegram telegram, SenderServices senderServices, BotUserServices botUserServices, WeatherService weatherService) {

        super(telegram.getBot().getToken());

        this.telegram = telegram;
        this.botUsername = telegram.getBot().getUsername();
        this.senderServices = senderServices;
        this.botUserServices = botUserServices;
        this.weatherService = weatherService;

        commandList.forEach(this::register);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        if (update.hasMyChatMember()) {
            handlerMyChatMember(update.getChatMember());
            return;
        }

        if (update.hasMessage()) {
            if (update.getMessage().hasLocation()) {
                handlerLocation(update.getMessage());
                return;
            }

        }

        String text = """
                Я не умею общаться в свободном стиле, лучше отправь мне команду из этого списка:
                                
                %s
                """;
        text = String.format(text, getCommandsDescriptor());
        Long userId = update.getMessage().getChatId();

        senderServices.sendBotMessage(this, text, userId);
    }

    private void handlerLocation(Message message) {

        double latitude = message.getLocation().getLatitude();
        double longitude = message.getLocation().getLongitude();
        botUserServices.updateLocation(message.getFrom(), latitude, longitude);
    }

    private void handlerMyChatMember(ChatMemberUpdated memberUpdated) {

        Boolean isPrivate = memberUpdated.getChat().isUserChat();
        String newStatus = memberUpdated.getNewChatMember().getStatus();
        botUserServices.updateBotUserStatus(newStatus, isPrivate, memberUpdated.getFrom());
    }

    private User getUserFromUpdate(Update update) {

        if (update.hasMessage()) {
            return update.getMessage().getFrom();
        }
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom();
        }
        if (update.hasInlineQuery()) {
            return update.getInlineQuery().getFrom();
        }
        if (update.hasChatMember()) {
            return update.getMyChatMember().getFrom();
        }

        return null;
    }

    private String getCommandsDescriptor() {
        Collection<IBotCommand> commandList = super.getRegisteredCommands();
        StringBuilder builder = new StringBuilder();
        commandList.forEach(command ->
                builder
                        .append("/")
                        .append(command.getCommandIdentifier())
                        .append("\t- ")
                        .append(command.getDescription())
                        .append(System.lineSeparator())
        );
        return builder.toString();
    }
}
