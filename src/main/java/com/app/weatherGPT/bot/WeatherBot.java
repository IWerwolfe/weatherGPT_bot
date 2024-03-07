package com.app.weatherGPT.bot;

import com.app.weatherGPT.config.Telegram;
import com.app.weatherGPT.model.BotUser;
import com.app.weatherGPT.model.location.City;
import com.app.weatherGPT.model.location.UserLocation;
import com.app.weatherGPT.repositories.CityRepository;
import com.app.weatherGPT.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class WeatherBot extends TelegramLongPollingCommandBot {

    private final CityRepository cityRepository;

    private final String botUsername;
    private final Telegram telegram;
    private final SenderServices senderServices;
    private final BotUserServices botUserServices;
    private final WeatherService weatherService;
    private final LocationServices locationServices;
    private final ButtonServices buttonServices;

    private BotUser botUser;

    public WeatherBot(
            List<IBotCommand> commandList,
            Telegram telegram,
            SenderServices senderServices,
            BotUserServices botUserServices,
            WeatherService weatherService,
            LocationServices locationServices,
            ButtonServices buttonServices,
            CityRepository cityRepository) {

        super(telegram.getBot().getToken());

        this.telegram = telegram;
        this.botUsername = telegram.getBot().getUsername();
        this.senderServices = senderServices;
        this.botUserServices = botUserServices;
        this.weatherService = weatherService;
        this.locationServices = locationServices;
        this.buttonServices = buttonServices;

        commandList.forEach(this::register);
        this.cityRepository = cityRepository;
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

        if (update.hasCallbackQuery()) {
            handlerCallbackQuery(update);
            return;
        }

        String text = """
                Я не умею общаться в свободном стиле, лучше отправь мне команду из этого списка:
                                
                %s
                """;
        text = String.format(text, getCommandsDescriptor());
        Long userId = update.getMessage().getChatId();

        senderServices.sendBotMessage(this, text, userId);
    }

    private void handlerCallbackQuery(Update update) {

        CallbackQuery query = update.getCallbackQuery();

        String data = query.getData();
        String[] param = data.split("%");
        String text;

        if (param.length < 2) {
            text = "Произошла при обработке команды";
            senderServices.sendBotEditMessage(this, text, query.getMessage().getChatId(), query.getMessage().getMessageId());
            return;
        }

        switch (param[0]) {
            case "city" -> setLocationByCity(query, param[1]);
            default -> {
                text = "Неизвестная команда";
                senderServices.sendBotEditMessage(this, text, query.getMessage().getChatId(), query.getMessage().getMessageId());
            }
        }
    }

    private void setLocationByCity(CallbackQuery query, String cityId) {

        String text;
        Optional<City> optional = cityRepository.findById(Long.valueOf(cityId));

        if (optional.isEmpty()) {
            text = "Произошла при обработке команды";
            log.error("Город с id {} не найден", cityId );
        } else {
            User user = query.getFrom();
            botUserServices.updateLocation(user, optional.get());
            text = "Установлен город " + optional.get().getName();
        }

        senderServices.sendBotEditMessage(this, text, query.getMessage().getChatId(), query.getMessage().getMessageId());
    }

    private void handlerLocation(Message message) {

        double latitude = message.getLocation().getLatitude();
        double longitude = message.getLocation().getLongitude();

        InlineKeyboardMarkup keyboard = null;
        String text;

        List<City> cityList = locationServices.searchLocation(latitude, longitude);

        switch (cityList.size()) {
            case 0 -> text = "К сожалению не удалось определить ваше местоположение";
            case 1 -> {
                City city = cityList.get(0);
                UserLocation location = new UserLocation(city);
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                botUserServices.updateLocation(message.getFrom(), location);

                text = city == null ?
                        "К сожалению не удалось определить город, в котором вы находитесь, мы установили положение по координатам" :
                        "Установлен город " + city.getName();
            }
            default -> {
                keyboard = buttonServices.getInlineCityKeyboard(cityList);
                text = "Мы нашли несколько городов с этими координатами, выберите нужный из них";
            }
        }
        senderServices.sendBotMessage(this, text, keyboard, message.getChatId());
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
