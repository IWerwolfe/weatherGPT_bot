package com.app.weatherGPT.service;

import com.app.weatherGPT.client.WeatherClient;
import com.app.weatherGPT.dto.api.weather.Location;
import com.app.weatherGPT.dto.api.weather.SearchResponse;
import com.app.weatherGPT.model.location.City;
import com.app.weatherGPT.model.location.Country;
import com.app.weatherGPT.model.location.Region;
import com.app.weatherGPT.model.location.UserLocation;
import com.app.weatherGPT.repositories.CityRepository;
import com.app.weatherGPT.repositories.CountryRepository;
import com.app.weatherGPT.repositories.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationServices {
    private final CityRepository cityRepository;
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;

    private final WeatherClient weatherClient;

    public List<UserLocation> searchLocation(double latitude, double longitude) {
        SearchResponse response = weatherClient.getCurrentSearchLocation(latitude, longitude);

        return null;
    }

    private List<UserLocation> convertToUserLocation(List<Location> locationList) {

        return locationList
                .stream()
                .map(this::convertToUserLocation)
                .toList();
    }

    private UserLocation convertToUserLocation(Location location) {

        Country country = convertToCountry(location.getCountry());
        Region region = convertToRegion(location.getRegion(), country);
        City city = convertToCity(location.getName(), region);

        UserLocation userLocation = new UserLocation(city);
        userLocation.setLongitude(location.getLon());
        userLocation.setLatitude(location.getLat());

        return userLocation;
    }

    private Country convertToCountry(String countryName) {

        if (countryName == null || countryName.isEmpty()) {
            return null;
        }

        Optional<Country> optional = countryRepository.findByNameIgnoreCase(countryName);
        if (optional.isEmpty()) {
            Country country = new Country(countryName);
            return countryRepository.save(country);
        }
        return optional.get();
    }

    private Region convertToRegion(String regionName, Country country) {

        if (regionName == null || regionName.isEmpty()) {
            return null;
        }

        Optional<Region> optional = regionRepository.findByNameIgnoreCaseAndCountry(regionName, country);
        if (optional.isEmpty()) {
            Region region = new Region(regionName, country);
            return regionRepository.save(region);
        }
        return optional.get();
    }

    private City convertToCity(String cityName, Region region) {

        if (cityName == null || cityName.isEmpty()) {
            return null;
        }

        Optional<City> optional = cityRepository.findByNameIgnoreCaseAndRegion(cityName, region);
        if (optional.isEmpty()) {
            City city = new City(cityName, region);
            return cityRepository.save(city);
        }
        return optional.get();
    }


}
