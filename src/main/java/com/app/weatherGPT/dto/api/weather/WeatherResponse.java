package com.app.weatherGPT.dto.api.weather;    /*
 *created by WerWolfe on WeatherResponse
 */

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

}
