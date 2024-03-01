package com.app.weatherGPT.service;

import com.app.weatherGPT.client.WeatherClient;
import com.app.weatherGPT.dto.api.weather.SearchResponse;
import com.app.weatherGPT.model.UserLocation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationServices {

    private final WeatherClient weatherClient;

    private UserLocation searchLocation(double latitude, double longitude) {
        SearchResponse response = weatherClient.getCurrentSearchLocation(latitude, longitude);
        return null;
    }
}
