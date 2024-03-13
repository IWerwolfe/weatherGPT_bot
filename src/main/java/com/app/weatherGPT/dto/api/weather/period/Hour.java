package com.app.weatherGPT.dto.api.weather.period;

import com.app.weatherGPT.dto.api.weather.description.AirQuality;
import com.app.weatherGPT.dto.api.weather.description.Condition;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hour {

    @JsonProperty("time_epoch")
    private long timeEpoch;

    private String time;

    @JsonProperty("temp_c")
    private double tempC;

    @JsonProperty("temp_f")
    private double tempF;

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

    @JsonProperty("pressure_in")
    private double pressureIn;

    @JsonProperty("precip_mm")
    private double precipMm;

    @JsonProperty("precip_in")
    private double precipIn;

    @JsonProperty("snow_cm")
    private double snowCm;

    private int humidity;
    private int cloud;

    @JsonProperty("feelslike_c")
    private double feelsLikeC;

    @JsonProperty("feelslike_f")
    private double feelsLikeF;

    @JsonProperty("windchill_c")
    private double windchillC;

    @JsonProperty("windchill_f")
    private double windchillF;

    @JsonProperty("heatindex_c")
    private double heatindexC;

    @JsonProperty("heatindex_f")
    private double heatindexF;

    @JsonProperty("dewpoint_c")
    private double dewpointC;

    @JsonProperty("dewpoint_f")
    private double dewpointF;

    @JsonProperty("will_it_rain")
    private int willItRain;

    @JsonProperty("will_it_snow")
    private int willItSnow;

    @JsonProperty("is_day")
    private int isDay;

    @JsonProperty("vis_km")
    private double visKm;

    @JsonProperty("vis_miles")
    private double visMiles;

    @JsonProperty("chance_of_rain")
    private int chanceOfRain;

    @JsonProperty("chance_of_snow")
    private int chanceOfSnow;

    @JsonProperty("gust_mph")
    private double gustMph;

    @JsonProperty("gust_kph")
    private double gustKph;

    private double uv;

    @JsonProperty("short_rad")
    private double shortRad;

    @JsonProperty("diff_rad")
    private double diffRad;

    @JsonProperty("air_quality")
    private AirQuality airQuality;

}
