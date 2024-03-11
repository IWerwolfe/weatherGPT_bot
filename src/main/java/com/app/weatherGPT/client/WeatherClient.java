package com.app.weatherGPT.client;    /*
 *created by WerWolfe on WeatherClient
 */

import com.app.weatherGPT.config.Weather;
import com.app.weatherGPT.dto.api.weather.Location;
import com.app.weatherGPT.dto.api.weather.WeatherResponse;
import com.app.weatherGPT.model.BotUser;
import com.app.weatherGPT.model.location.UserLocation;
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
    public String getUrl() {;
        return url;
    }

    public String getUrlCurrentWeather() {
        StringBuilder builder = new StringBuilder();
        builder.append(weather.getUrlCurrent())
                .append("?key=")
                .append(weather.getToken())
                .append(getLocation())
                .append("&lang=")
                .append(getLang());
        return builder.toString();
    }

    public String getUrlSearch(String query) {
        StringBuilder builder = new StringBuilder();
        builder.append(weather.getUrlSearch())
                .append("?key=")
                .append(weather.getToken())
                .append(query)
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
        return executeGetRequest(WeatherResponse.class, getUrlCurrentWeather());
    }

    public Location[] getCurrentSearchLocation(double latitude, double longitude) {
        String query = getLocation(latitude, longitude);
        return executeGetRequest(Location[].class, getUrlSearch(query));
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

        if (!(location.getLatitude() == null || location.getLongitude() == null)) {
            return getLocation(location.getLatitude(), location.getLongitude());
        }

        if (location.getCity() != null) {
            return String.format("&q=%s", location.getCityName());
        }

        return getLocation(0, 0);
    }

    private String getLocation(double latitude, double longitude) {
        return String.format("&q=%s,%s", latitude, longitude);
    }
}
