package com.app.weatherGPT.service;    /*
 *created by WerWolfe on WeatherService
 */

import com.app.weatherGPT.client.WeatherClient;
import com.app.weatherGPT.dto.api.weather.WeatherResponse;
import com.app.weatherGPT.dto.api.weather.description.AirQuality;
import com.app.weatherGPT.dto.api.weather.description.Astro;
import com.app.weatherGPT.dto.api.weather.description.Condition;
import com.app.weatherGPT.dto.api.weather.enums.*;
import com.app.weatherGPT.dto.api.weather.period.CurrentWeather;
import com.app.weatherGPT.dto.api.weather.period.Day;
import com.app.weatherGPT.dto.api.weather.period.Forecast;
import com.app.weatherGPT.dto.api.weather.period.ForecastDay;
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

    public String getDescCurrentWeather(BotUser botUser) {

        WeatherResponse weather = weatherClient.getCurrentWeather(botUser);

        if (weather == null) {
            return "The weather forecast could not be obtained.";
        }

        return toTextDescCurrent(weather);
    }

    public String getDescForecast(BotUser botUser, int days) {

        WeatherResponse weather = weatherClient.getForecast(botUser, days);

        if (weather == null) {
            return "The weather forecast could not be obtained.";
        }

        return toTextDescForecast(weather);
    }

    private String toTextDescForecast(WeatherResponse weatherResponse) {

        StringBuilder builder = new StringBuilder();
        Forecast forecast = weatherResponse.getForecast();

        builder
                .append("Прогноз погоды в ")
                .append(weatherResponse.getLocation().getName());

        forecast.getForecastDay().forEach(forecastDay -> builder
                .append(System.lineSeparator())
                .append(toTextDescForecastDay(forecastDay))
                .append("---------------------------------------------------------------"));

        return builder.toString();
    }

    private String toTextDescForecastDay(ForecastDay forecastDay) {

        Day day = forecastDay.getDay();
        Condition condition = day.getCondition();
        Astro astro = forecastDay.getAstro();

        String maxTemp = String.valueOf(day.getMaxTempC());
        String minTemp = String.valueOf(day.getMinTempC());
        String avgTemp = String.valueOf(day.getAvgTempC());

        String astroDesc = toTextDescAstro(astro);
        String precipitation = toTextDescPrecipitation(day);

        String description = condition.getText().toLowerCase();
        String wind = toTextDescWind(day.getMaxWindKph());

        String humidity = toTextDescHumidity(day.getAvgHumidity());
        String uv = toTextDescUVIndex(day.getUv());
        String airQ = toTextDescAirQuality(forecastDay.getAirQuality());

        String pattern = """
                
                %s
                температура от %s до %s °C
                %s
                %s
                %s
                                
                %s
                Ультрафиолетовое излучение: %s
                Качество воздуха (US EPA Index): %s
                                
                %s""";

        return String.format(pattern,
                forecastDay.getDate(),
                minTemp,
                maxTemp,
                description,
                wind,
                precipitation,
                humidity,
                uv,
                airQ,
                astroDesc);
    }

    private String toTextDescCurrent(WeatherResponse weatherResponse) {

        CurrentWeather current = weatherResponse.getCurrent();
        Condition condition = current.getCondition();

        String city = weatherResponse.getLocation().getName();
        String temp = String.valueOf(current.getTempC());
        String description = condition.getText().toLowerCase();
        String wind = toTextDescWind(current.getWindKph(), current.getGustKph(), current.getWindDir());
        String humidity = toTextDescHumidity(current.getHumidity());
        String uv = toTextDescUVIndex(current.getUv());
        String airQ = toTextDescAirQuality(current.getAirQuality());

        String pattern = """
                Текущая погода в %s
                                
                темп. %s °C, %s
                %s
                %s
                                
                Ультрафиолетовое излучение: %s
                Качество воздуха (US EPA Index): %s""";

        return String.format(pattern, city, temp, description, wind, humidity, uv, airQ);
    }

    private String toTextDescAstro(Astro astro) {

        String sunrise = textUtil.time12In24Format(astro.getSunrise());
        String sunset = textUtil.time12In24Format(astro.getSunset());
        String moon = ConverterUtil.convertToDescEnum(astro.getMoonPhase(), LunarPhase.class);

        String pattern = """                
                Рассвет %s
                Закат %s
                %s
                """;

        return String.format(pattern, sunrise, sunset, moon);
    }

    private String toTextDescPrecipitation(Day day) {

        StringBuilder builder = new StringBuilder();

        if (day.getWillItRain() == 1) {

            builder
                    .append(variablePrecipitation(day.getChanceOfRain()))
                    .append(" ")
                    .append(variableRain(day.getTotalPrecipMm()));
            return builder.toString();
        }

        if (day.getWillItSnow() == 1) {

            builder
                    .append(variablePrecipitation(day.getChanceOfSnow()))
                    .append(" ")
                    .append(variableSnow(day.getTotalPrecipMm()));
            return builder.toString();
            }

        return builder
                .append("Без осадков")
                .toString();
    }

    private String variableSnow(double totalPrecipMm) {

        if (totalPrecipMm <= 3) {
            return "слабый снег";
        }
        if (totalPrecipMm <= 6) {
            return "снегопад";
        }
        if (totalPrecipMm <= 19) {
            return "сильный снегопад";
        }
        return "очень сильный снегопад";
    }

    private String variableRain(double totalPrecipMm) {

        if (totalPrecipMm <= 3) {
            return "легкий дождь";
        }
        if (totalPrecipMm <= 14) {
            return "дождь";
        }
        if (totalPrecipMm <= 49) {
            return "сильный дождь";
        }
        return "ливень";
    }

    private String variablePrecipitation(int percent) {

        if (percent < 50) {
            return "Возможен";
        }
        if (percent < 75) {
            return "Вероятно будет";
        }
        return "Будет";
    }

    private String toTextDescUVIndex(double uvIndex) {

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

    private String toTextDescAirQuality(AirQuality airQuality) {

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


    private String toTextDescHumidity(int humidity) {

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

    private String toTextDescWind(double windKph, double gustKph, String windDir) {

        double wildSpeed = ConverterUtil.convertKmPerHourToMetersPerSecond(windKph);
        double gustSpeed = ConverterUtil.convertKmPerHourToMetersPerSecond(gustKph);

        if (wildSpeed <= 1) {
            return "Безветренно";
        }

        String pattern = "%s ветер до %s м/с";
        String enumDescWind = ConverterUtil.convertToDescEnum(windDir, CardinalDirection.class);
        String windDirection = textUtil.getFormWords(enumDescWind);
        String wildSpeedString = ConverterUtil.formatToString(wildSpeed, "0.0");

        if (gustSpeed < 1) {
            return String.format(pattern, windDirection, wildSpeedString);
        }

        pattern = pattern + System.lineSeparator() + "с порывами до %s м/с";
        String gustSpeedString = ConverterUtil.formatToString(gustSpeed, "0.0");
        return String.format(pattern, windDirection, wildSpeedString, gustSpeedString);
    }

    private String toTextDescWind(double windKph) {

        double wildSpeed = ConverterUtil.convertKmPerHourToMetersPerSecond(windKph);

        if (wildSpeed <= 1) {
            return "Безветренно";
        }

        String pattern = "Ветер до %s м/с";
        String wildSpeedString = ConverterUtil.formatToString(wildSpeed, "0.0");

        return String.format(pattern, wildSpeedString);
    }
}
