package com.app.weatherGPT.client;    /*
 *created by WerWolfe on WeatherClient
 */

import com.app.weatherGPT.config.Weather;
import com.app.weatherGPT.dto.api.weather.WeatherResponse;
import com.app.weatherGPT.model.BotUser;
import com.app.weatherGPT.model.UserLocation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherClient extends ClientHttp {

    private final Weather weather;
    private String url;
    private BotUser botUser;

    @Override
    public String getUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append(weather.getUrl())
                .append("?key=")
                .append(weather.getToken())
                .append(getLocation())
                .append("&lang=")
                .append(getLang());
        return builder.toString();
    }

    @Override
    public HttpHeaders getHttpHeaders() {
        return new HttpHeaders();
    }

    public WeatherResponse getCurrentWeather(BotUser botUser) {
        this.botUser = botUser;
        return executeGetRequest(WeatherResponse.class, getUrl());
    }

    private String getLang() {
        if (botUser == null || botUser.getLanguage_code() == null) {
            return weather.getLang();
        }
        return  botUser.getLanguage_code().name().toLowerCase();
    }

    private String getLocation() {
        boolean is = (botUser == null || botUser.getLocation() == null);
        UserLocation location = is ? UserLocation.getDefaultLocation() : botUser.getLocation();

        if (location.getCity() == null) {
            return String.format("&q=%s,%s", location.getLatitude(), location.getLongitude());
        }

        if (location.getLatitude() == null || location.getLongitude() == null) {
            return String.format("&q=%s,%s", 0, 0);
        }
        return String.format("&q=%s", location.getCityName());
    }
}
