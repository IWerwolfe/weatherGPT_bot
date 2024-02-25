package com.app.weatherGPT.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class TextUtil {

//    private JMorfSdk jMorfSdk = JMorfSdkFactory.loadFullLibrary();

    public String shortenText(String text, int size) {

        if (text == null || text.isEmpty()) {
            return "";
        }

        return text.length() < size ?
                text :
                text.substring(0, size - 3) + "...";
    }

    public String getDescriptor(String text) {

        if (text == null || text.isEmpty()) {
            return "";
        }

        String REGEX = "[^0-9a-zA-Zа-яА-ЯёЁ\\-.,_:!?()]+";
        return text.replaceAll(REGEX, " ").replaceAll("\s{2,}", " ").trim();
    }

    public String[] splitTextByPattern(String text, String regex) {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        String[] str = new String[2];

        if (matcher.find()) {
            str[0] = matcher.group(1);
            str[1] = matcher.group(2);
        }
        return str;
    }

    public String[] splitText(String text) {

        int newlineIndex = text.indexOf('\n');
        String[] str = new String[2];

        if (newlineIndex != -1) {
            str[0] = text.substring(0, newlineIndex);
            str[1] = text.substring(newlineIndex + 1);
        }

        return str;
    }

    public String getFormWords(String word) {

        switch (word) {
            case "Север":
                return "Северный";
            case "Юг":
                return "Южный";
//            case "Запад" : return "Западный";
//            case "Восток" : return "Восточный";
            default: {
                if (word.endsWith("осток")) {
                    return word.replaceAll("осток", "осточный");
                }
                if (word.endsWith("апад")) {
                    return word.replaceAll("апад", "ападный");
                }
            }
        }
        return word;
    }
}
