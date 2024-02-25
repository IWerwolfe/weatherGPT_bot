package com.app.weatherGPT.service;    /*
 *created by WerWolfe on WeatherService
 */

import com.app.weatherGPT.client.WeatherClient;
import com.app.weatherGPT.dto.api.weather.*;
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

    public String getDescriptorCurrentWeather() {

        WeatherResponse weather = weatherClient.getCurrentWeather();

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
        String description = condition.getText();
        String wind = addInfoAboutWind(current);
        String humidity = getDescriptorHumidity(current.getHumidity());
        String uv = getDescriptorUVIndex(current.getUv());

        String pattern = """
                Текущая погода в %s
                                
                темп. %s, %s
                %s
                %s
                Ультрафиолетовое излучение: %s""";

        return String.format(pattern, city, temp, description, wind, humidity, uv);
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

        String pattern = "";
        String windDirection = textUtil.getFormWords(getDescriptorWindDirection(current.getWind_dir()));
        String wildSpeedString = ConverterUtil.formatToString(wildSpeed, "0.0");

        if (gustSpeed >= 1) {
            pattern = "%s ветер до %s м/с, с порывами до %s  м/с";
            String gustSpeedString = ConverterUtil.formatToString(gustSpeed, "0.0");
            return String.format(pattern, windDirection, wildSpeedString, gustSpeedString);
        }

        pattern = "%s ветер до %s м/с";
        return String.format(pattern, windDirection, wildSpeedString);
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
            return "безветренно";
        }
    }
}
