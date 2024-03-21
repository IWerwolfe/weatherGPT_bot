package com.app.weatherGPT.service;

import com.app.weatherGPT.dto.Frequency;
import com.app.weatherGPT.model.location.City;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ButtonServices {

    private static final int SIZE_INLINE_BUTTON = 64;

    private static final KeyboardButton EXIT = new KeyboardButton("Отмена");
    private static final KeyboardButton LOCATION = new KeyboardButton("Мое местоположение");
    private final String timeFormat = "%02d:%02d";

    static {
        LOCATION.setRequestLocation(true);
    }

    public InlineKeyboardMarkup getInlineCityKeyboard(List<City> cityList) {

        List<List<InlineKeyboardButton>> buttonList = new ArrayList<>();

        for (City city : cityList) {
            if (city == null) {
                continue;
            }
            String command = "city%" + city.getId();
            buttonList.add(getInlineKeyboardButtonOfRow(city.getName(), command));
        }
        return createInlineKeyboardMarkup(buttonList);
    }

    public <T extends Enum<T>> InlineKeyboardMarkup getInlineEnumKeyboard(Class<T> enumClass, String commandName) {

        List<List<InlineKeyboardButton>> buttonList = new ArrayList<>();
        EnumSet<T> enumSet = EnumSet.allOf(enumClass);

        for (T field : enumSet) {
            String command = commandName + "%" + field.name();
            buttonList.add(getInlineKeyboardButtonOfRow(field.toString(), command));
        }
        return createInlineKeyboardMarkup(buttonList);
    }


    public InlineKeyboardMarkup getInlineTime() {

        List<List<InlineKeyboardButton>> buttonList = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        for (int i = 0; i < 24; i++) {

            if (i%6 == 0) {
                buttonList.add(row);
                row = new ArrayList<>();
            }

            String time = String.format(timeFormat, i, 0);
            String command = "time%" + time;
            row.add(getInlineKeyboardButton(time, command));
        }

        if (row.size() > 0) {
            buttonList.add(row);
        }

        return createInlineKeyboardMarkup(buttonList);
    }

    public ReplyKeyboardMarkup keyboardMarkupCommands() {
        return createReplyKeyboardMarkup(EXIT, false);
    }

    public ReplyKeyboardMarkup keyboardMarkupGetLocation() {
        return createReplyKeyboardMarkup(LOCATION, true);
    }

    public InlineKeyboardMarkup getEmptyInlineKeyboardMarkup() {
        return createInlineKeyboardMarkup(new ArrayList<>());
    }

    private String convertDescription(String code) {
        String REGEX = "[^0-9a-zA-Zа-яА-ЯёЁ\\-.,=_*+&:#№@!/(){}\\[\\]]+";
        return code
                .replaceAll(REGEX, " ")
                .replaceAll("\s{2,}", " ")
                .trim();
    }

    private ReplyKeyboardMarkup createReplyKeyboardMarkup(KeyboardButton button, boolean oneTimeKeyboard) {
        KeyboardRow row = new KeyboardRow();
        row.add(button);
        return createReplyKeyboardMarkup(List.of(row), oneTimeKeyboard);
    }

    private ReplyKeyboardMarkup createReplyKeyboardMarkup(List<KeyboardRow> rows, boolean oneTimeKeyboard) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(oneTimeKeyboard);
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }

    private ReplyKeyboardMarkup createReplyKeyboardMarkup(List<KeyboardRow> rows) {
        return createReplyKeyboardMarkup(rows, true);
    }

    private InlineKeyboardButton getInlineKeyboardButton(String label, String command) {
        InlineKeyboardButton taskButton = new InlineKeyboardButton(label);
        taskButton.setCallbackData(command);
        return taskButton;
    }

    private List<InlineKeyboardButton> getInlineKeyboardButtonOfRow(String label, String command) {
        return List.of(getInlineKeyboardButton(label, command));
    }

    private InlineKeyboardMarkup createInlineKeyboardMarkup(List<List<InlineKeyboardButton>> rows) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rows);
        return markupInline;
    }
}
