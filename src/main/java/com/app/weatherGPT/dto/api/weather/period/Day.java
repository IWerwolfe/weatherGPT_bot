package com.app.weatherGPT.dto.api.weather.period;

import com.app.weatherGPT.dto.api.weather.description.Condition;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Day {

    @JsonProperty("maxtemp_c")
    private double maxTempC;

    @JsonProperty("maxtemp_f")
    private double maxTempF;

    @JsonProperty("mintemp_c")
    private double minTempC;

    @JsonProperty("mintemp_f")
    private double minTempF;

    @JsonProperty("avgtemp_c")
    private double avgTempC;

    @JsonProperty("avgtemp_f")
    private double avgTempF;

    @JsonProperty("maxwind_mph")
    private double maxWindMph;

    @JsonProperty("maxwind_kph")
    private double maxWindKph;

    @JsonProperty("totalprecip_mm")
    private double totalPrecipMm;

    @JsonProperty("totalprecip_in")
    private double totalPrecipIn;

    @JsonProperty("totalsnow_cm")
    private double totalSnowCm;

    @JsonProperty("avgvis_km")
    private double avgVisKm;

    @JsonProperty("avgvis_miles")
    private double avgVisMiles;

    @JsonProperty("avghumidity")
    private int avgHumidity;

    private Condition condition;
    private double uv;

    @JsonProperty("daily_will_it_rain")
    private int willItRain;

    @JsonProperty("daily_will_it_snow")
    private int willItSnow;

    @JsonProperty("daily_chance_of_rain")
    private int chanceOfRain;

    @JsonProperty("daily_chance_of_snow")
    private int chanceOfSnow;
}
