package com.app.weatherGPT.service;    /*
 *created by WerWolfe on WeatherService
 */

import com.app.weatherGPT.client.WeatherClient;
import com.app.weatherGPT.dto.api.weather.*;
import com.app.weatherGPT.dto.api.weather.description.AirQuality;
import com.app.weatherGPT.dto.api.weather.description.Condition;
import com.app.weatherGPT.dto.api.weather.enums.CardinalDirection;
import com.app.weatherGPT.dto.api.weather.enums.Humidity;
import com.app.weatherGPT.dto.api.weather.enums.UVIndex;
import com.app.weatherGPT.dto.api.weather.enums.UsEpaIndex;
import com.app.weatherGPT.dto.api.weather.period.CurrentWeather;
import com.app.weatherGPT.model.BotUser;
import com.app.weatherGPT.utils.ConverterUtil;
import com.app.weatherGPT.utils.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;
    private final TextUtil textUtil;

    public String getDescriptorCurrentWeather(BotUser botUser) {

        WeatherResponse weather = weatherClient.getCurrentWeather(botUser);

        if (weather == null) {
            return "The weather forecast could not be obtained.";
        }

        return toTextDescription(weather);
    }

    public String toTextDescription(WeatherResponse weatherResponse) {

        CurrentWeather current = weatherResponse.getCurrent();
        Condition condition = current.getCondition();

        String city = weatherResponse.getLocation().getName();
        String temp = String.valueOf(current.getTemp_c());
        String description = condition.getText().toLowerCase();
        String wind = addInfoAboutWind(current);
        String humidity = getDescriptorHumidity(current.getHumidity());
        String uv = getDescriptorUVIndex(current.getUv());
        String airQ = getDescriptorAirQuality(current.getAir_quality());

        String pattern = """
                Текущая погода в %s
                                
                темп. %s °C, %s
                %s
                %s
                Ультрафиолетовое излучение: %s
                Качество воздуха (US EPA Index): %s""";

        return String.format(pattern, city, temp, description, wind, humidity, uv, airQ);
    }

    public String getDescriptorUVIndex(double uvIndex) {

        if (uvIndex <= 2) {
            return UVIndex.LOW.getLabel();
        }
        if (uvIndex <= 5) {
            return UVIndex.MEDIUM.getLabel();
        }
        if (uvIndex <= 7) {
            return UVIndex.HIGH.getLabel();
        }
        if (uvIndex <= 10) {
            return UVIndex.VERY_HIGH.getLabel();
        }
        return UVIndex.EXTREME.getLabel();
    }

    public String getDescriptorAirQuality(AirQuality airQuality) {

        if (airQuality == null) {
            return "--";
        }

        return switch (airQuality.getUsEpaIndex()) {
            case 0, 1 -> UsEpaIndex.GOOD.getLabel();
            case 2 -> UsEpaIndex.MODERATE.getLabel();
            case 3 -> UsEpaIndex.UNHEALTHY_SENSITIVE.getLabel();
            case 4 -> UsEpaIndex.UNHEALTHY.getLabel();
            case 5 -> UsEpaIndex.VERY_UNHEALTHY.getLabel();
            default -> UsEpaIndex.HAZARDOUS.getLabel();
        };
    }


    private String getDescriptorHumidity(int humidity) {
        if (humidity <= 30) {
            return Humidity.LOW.getLabel();
        }
        if (humidity <= 60) {
            return Humidity.MEDIUM.getLabel();
        }
        if (humidity <= 75) {
            return Humidity.HIGH.getLabel();
        }
        return Humidity.VERY_HIGH.getLabel();
    }

    private String addInfoAboutWind(CurrentWeather current) {

        double wildSpeed = ConverterUtil.convertKmPerHourToMetersPerSecond(current.getWind_kph());
        double gustSpeed = ConverterUtil.convertKmPerHourToMetersPerSecond(current.getGust_kph());

        if (wildSpeed <= 1) {
            return "Безветренно";
        }

        String pattern = "%s ветер до %s м/с";
        String windDirection = textUtil.getFormWords(getDescriptorWindDirection(current.getWind_dir()));
        String wildSpeedString = ConverterUtil.formatToString(wildSpeed, "0.0");

        if (gustSpeed < 1) {
            return String.format(pattern, windDirection, wildSpeedString);
        }

        pattern = pattern + System.lineSeparator() + "с порывами до %s м/с";
        String gustSpeedString = ConverterUtil.formatToString(gustSpeed, "0.0");
        return String.format(pattern, windDirection, wildSpeedString, gustSpeedString);
    }

    private String getDescriptorWindDirection(String windDirection) {

        if (windDirection == null || windDirection.isEmpty()) {
            return "безветренно";
        }

        try {
            return CardinalDirection.valueOf(windDirection).getLabel();
        } catch (Exception e) {
            log.error("An error occurred while retrieving the value {} from the enumeration {}.",
                    CardinalDirection.class.getName(),
                    windDirection
            );
            log.error(e.getMessage());
            return "ветренно";
        }
    }
}
