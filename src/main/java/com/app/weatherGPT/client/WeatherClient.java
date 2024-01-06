package com.app.weatherGPT.client;    /*
 *created by WerWolfe on WeatherClient
 */

import com.app.weatherGPT.config.Weather;
import com.app.weatherGPT.dto.api.weather.WeatherResponse;
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

    @Override
    public String getUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append(weather.getUrl())
                .append("?key=")
                .append(weather.getToken())
                .append("&q=50.550,137.007")
                .append("&lang=")
                .append(weather.getLang());
        return builder.toString();
    }

    @Override
    public HttpHeaders getHttpHeaders() {
        return new HttpHeaders();
    }

    public WeatherResponse getCurrentWeather() {
        return executeGetRequest(WeatherResponse.class, getUrl());
    }

}
