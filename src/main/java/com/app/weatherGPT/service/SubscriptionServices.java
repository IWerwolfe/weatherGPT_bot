package com.app.weatherGPT.service;

import com.app.weatherGPT.client.WeatherClient;
import com.app.weatherGPT.dto.api.weather.description.Location;
import com.app.weatherGPT.model.BotUser;
import com.app.weatherGPT.model.Subscription;
import com.app.weatherGPT.model.location.City;
import com.app.weatherGPT.model.location.Country;
import com.app.weatherGPT.model.location.Region;
import com.app.weatherGPT.model.location.UserLocation;
import com.app.weatherGPT.repositories.CityRepository;
import com.app.weatherGPT.repositories.CountryRepository;
import com.app.weatherGPT.repositories.RegionRepository;
import com.app.weatherGPT.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServices {

    private final SubscriptionRepository subscriptionRepository;

    public List<Subscription> getSubscriptions(BotUser botUser) {
        return subscriptionRepository.findByUser(botUser);
    }

}
