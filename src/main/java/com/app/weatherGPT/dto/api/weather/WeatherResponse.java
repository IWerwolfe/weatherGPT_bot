package com.app.weatherGPT.dto.api.weather;    /*
 *created by WerWolfe on WeatherResponse
 */

import com.app.weatherGPT.dto.api.weather.description.Astro;
import com.app.weatherGPT.dto.api.weather.description.Astronomy;
import com.app.weatherGPT.dto.api.weather.description.Location;
import com.app.weatherGPT.dto.api.weather.period.CurrentWeather;
import com.app.weatherGPT.dto.api.weather.period.Forecast;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse implements Serializable {

    private Location location;
    private CurrentWeather current;
    private Forecast forecast;
    private Astronomy astronomy;

}
