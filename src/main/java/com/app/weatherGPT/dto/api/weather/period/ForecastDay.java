package com.app.weatherGPT.dto.api.weather.period;

import com.app.weatherGPT.dto.api.weather.description.AirQuality;
import com.app.weatherGPT.dto.api.weather.description.Astro;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastDay {

    private String date;

    @JsonProperty("date_epoch")
    private long dateEpoch;

    private Day day;

    private Astro astro;

    @JsonProperty("air_quality")
    private AirQuality airQuality;

    @JsonProperty("hour")
    private List<Hour> hours;
}
