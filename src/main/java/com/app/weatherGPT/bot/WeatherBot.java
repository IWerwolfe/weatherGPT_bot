package com.app.weatherGPT.bot;

import com.app.weatherGPT.config.Telegram;
import com.app.weatherGPT.model.BotUser;
import com.app.weatherGPT.repositories.BotUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Collection;
import java.util.List;


@Service
@Slf4j
public class WeatherBot extends TelegramLongPollingCommandBot {
    private final BotUserRepository botUserRepository;
    private final String botUsername;
    private final Telegram telegram;
    private final Sender sender;

    private BotUser botUser;

    public WeatherBot(
            List<IBotCommand> commandList,
            Telegram telegram, Sender sender,
            BotUserRepository botUserRepository) {

        super(telegram.getBot().getToken());

        this.telegram = telegram;
        this.botUsername = telegram.getBot().getUsername();
        this.sender = sender;
        this.botUserRepository = botUserRepository;

        commandList.forEach(this::register);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        User user = getUserFromUpdate(update);
        botUser = getBotUser(user);

        if (update.hasMyChatMember()) {
            handlerMyChatMember(update);
            return;
        }

        String text = """
                Я не умею общаться в свободном стиле, лучше отправь мне команду из этого списка:
                                
                %s
                """;
        text = String.format(text, getCommandsDescriptor());
        Long userId = update.getMessage().getChatId();

        sender.sendBotMessage(this, text, userId);
    }

    private void handlerMyChatMember(Update update) {

        Boolean isPrivate = update.getMyChatMember().getChat().isUserChat();
        String newStatus = update.getMyChatMember().getNewChatMember().getStatus();
        updateBotUserStatus(newStatus, isPrivate);
    }

    private void updateBotUserStatus(String newStatus, boolean isPrivate) {

        String text;

        if (botUser == null) {
            text = String.format("Ошибка при изменении статуса %s, не удалось определить пользователя", newStatus);
        } else {
            botUser.setIsValid(!newStatus.equals("kicked") && isPrivate);
            botUserRepository.save(botUser);
            text = String.format("У пользователя с id: %s изменился статус на %s", botUser.getId(), newStatus);
        }
        log.warn(text);
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

    private BotUser getBotUser(User user) {

        if (user == null) {
            return null;
        }

        return botUserRepository
                .findByTelegramId(user.getId())
                .orElse(new BotUser(user));
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
