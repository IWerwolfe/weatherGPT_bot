package com.app.weatherGPT.dto.api.weather.period;    /*
 *created by WerWolfe on CurrentWeather
 */

import com.app.weatherGPT.dto.api.weather.description.AirQuality;
import com.app.weatherGPT.dto.api.weather.description.Condition;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeather implements Serializable {

    @JsonProperty("last_updated_epoch")
    private long lastUpdatedEpoch;

    @JsonProperty("last_updated")
    private String lastUpdated;

    @JsonProperty("temp_c")
    private double tempC;

    @JsonProperty("is_day")
    private int isDay;

    private Condition condition;

    @JsonProperty("wind_mph")
    private double windMph;

    @JsonProperty("wind_kph")
    private double windKph;

    @JsonProperty("wind_degree")
    private int windDegree;

    @JsonProperty("wind_dir")
    private String windDir;

    @JsonProperty("pressure_mb")
    private double pressureMb;

    @JsonProperty("precip_mm")
    private double precipMm;

    private int humidity;

    private int cloud;

    @JsonProperty("feelslike_c")
    private double feelslikeC;

    @JsonProperty("vis_km")
    private double visKm;

    private double uv;

    @JsonProperty("gust_kph")
    private double gustKph;

    @JsonProperty("air_quality")
    private AirQuality airQuality;
}
