package com.app.weatherGPT.dto.api.weather.period;    /*
 *created by WerWolfe on CurrentWeather
 */

import com.app.weatherGPT.dto.api.weather.description.AirQuality;
import com.app.weatherGPT.dto.api.weather.description.Condition;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeather implements Serializable {

    private long last_updated_epoch;
    private String last_updated;
    private double temp_c;
    private int is_day;
    private Condition condition;
    private double wind_mph;
    private double wind_kph;
    private int wind_degree;
    private String wind_dir;
    private double pressure_mb;
    private double precip_mm;
    private int humidity;
    private int cloud;
    private double feelslike_c;
    private double vis_km;
    private double uv;
    private double gust_kph;
    private AirQuality air_quality;
}
