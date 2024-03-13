package com.app.weatherGPT.dto.api.weather.period;

import com.app.weatherGPT.dto.api.weather.description.AirQuality;
import com.app.weatherGPT.dto.api.weather.description.Astro;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ForecastDay {

    private String date;

    @JsonProperty("date_epoch")
    private long dateEpoch;

    private Day day;
    private Astro astro;

    @JsonProperty("air_quality")
    private AirQuality airQuality;

    private Hour hour;
}
