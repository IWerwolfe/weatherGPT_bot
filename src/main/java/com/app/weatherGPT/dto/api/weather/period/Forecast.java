package com.app.weatherGPT.dto.api.weather.period;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {

    @JsonProperty("forecastday")
    private List<ForecastDay> forecastDay;
}
