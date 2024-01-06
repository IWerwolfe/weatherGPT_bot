package com.app.weatherGPT.service;    /*
 *created by WerWolfe on WeatherService
 */

import com.app.weatherGPT.client.WeatherClient;
import com.app.weatherGPT.dto.api.weather.CardinalDirection;
import com.app.weatherGPT.dto.api.weather.Condition;
import com.app.weatherGPT.dto.api.weather.CurrentWeather;
import com.app.weatherGPT.dto.api.weather.WeatherResponse;
import com.app.weatherGPT.utils.ConverterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;

    public String getDescriptorCurrentWeather() {

        WeatherResponse weather = weatherClient.getCurrentWeather();

        if (weather == null) {
            return "The weather forecast could not be obtained.";
        }

        return toTextDescription(weather);
    }

    public String toTextDescription(WeatherResponse weatherResponse) {

        StringBuilder descriptor = new StringBuilder();
        CurrentWeather current = weatherResponse.getCurrent();
        Condition condition = current.getCondition();

        descriptor.append("Текущая погода в ")
                .append(weatherResponse.getLocation().getName())
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("темп. ")
                .append(current.getTemp_c())
                .append(" °C, ")
                .append(condition.getText())
                .append(System.lineSeparator());

        addInfoAboutWind(descriptor, current);
        descriptor.append(System.lineSeparator());
        descriptor.append(getDescriptorHumidity(current.getHumidity()));
        descriptor.append(System.lineSeparator());
        descriptor.append("Ультрафиолетовое излучение: ")
                .append(getDescriptorUVIndex(current.getUv()));

        return descriptor.toString();
    }

    public String getDescriptorUVIndex(double uvIndex) {

        if (uvIndex <= 2) {
            return "Низкое";
        }
        if (uvIndex <= 5) {
            return "Умеренное";
        }
        if (uvIndex <= 7) {
            return "Высокое";
        }
        if (uvIndex <= 10) {
            return "Очень высокое";
        }
        return "Экстремальное";
    }

    private String getDescriptorHumidity(int Humidity) {
        if (Humidity <= 30) {
            return "Очень сухой воздух";
        }
        if (Humidity <= 60) {
            return "Нормальная влажность";
        }
        if (Humidity <= 75) {
            return "Повышенная влажность";
        }
        return "Очень высокая влажность";
    }

    private void addInfoAboutWind(StringBuilder descriptor, CurrentWeather current) {

        double wildSpeed = ConverterUtil.convertKmPerHourToMetersPerSecond(current.getWind_kph());
        if (wildSpeed >= 1) {
            descriptor.append("Ветер ")
                    .append(getDescriptorWindDirection(current.getWind_dir()))
                    .append(", до ")
                    .append(ConverterUtil.formatToString(wildSpeed, "0.0"))
                    .append(" м\\с");
        }

        double gustSpeed = ConverterUtil.convertKmPerHourToMetersPerSecond(current.getGust_kph());
        if (gustSpeed >= 1) {
            descriptor.append(" с порывами до ")
                    .append(ConverterUtil.formatToString(gustSpeed, "0.0"))
                    .append(" м\\с");
        }
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
