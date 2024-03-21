package com.app.weatherGPT.service;

import com.app.weatherGPT.client.WeatherClient;
import com.app.weatherGPT.dto.api.weather.description.Location;
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

import java.time.ZoneId;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationServices {
    private final CityRepository cityRepository;
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;
    private final WeatherClient weatherClient;

    public List<City> searchLocation(double latitude, double longitude) {

        Location[] response = weatherClient.getCurrentSearchLocation(latitude, longitude);
        return response == null ?
                new ArrayList<>() :
                convertToUserLocation(response);
    }

    private List<City> convertToUserLocation(Location[] locationList) {

        return Arrays
                .stream(locationList)
                .map(this::convertToCity)
                .toList();
    }

    private UserLocation convertToUserLocation(Location location) {

        City city = convertToCity(location);
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

    private City convertToCity(Location location) {

        if (location.getName() == null || location.getName().isEmpty()) {
            return null;
        }

        List<City> cityList = cityRepository.findByNameIgnoreCase(location.getName());
        for (City city : cityList) {
            if (city.getRegion().getName().equalsIgnoreCase(location.getRegion())) {
                return city;
            }
        }

        Region region = convertToRegion(location);
        City city = new City(location.getName(), region);
        city.setZoneId(ZoneId.of(location.getTzId()));
        return cityRepository.save(city);
    }

    private Region convertToRegion(Location location) {

        if (location.getRegion() == null || location.getRegion().isEmpty()) {
            return null;
        }

        List<Region> regionList = regionRepository.findByNameIgnoreCase(location.getRegion());
        for (Region region : regionList) {
            if (region.getCountry().getName().equalsIgnoreCase(location.getCountry())) {
                return region;
            }
        }

        Country country = convertToCountry(location.getCountry());
        Region region = new Region(location.getRegion(), country);
        return regionRepository.save(region);
    }
}
