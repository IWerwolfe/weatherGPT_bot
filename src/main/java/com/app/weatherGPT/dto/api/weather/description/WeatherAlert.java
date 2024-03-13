package com.app.weatherGPT.dto.api.weather.description;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherAlert {

    @JsonProperty("msgType")
    private String messageType;

    private String headline;
    private String severity;
    private String urgency;
    private String areas;
    private String category;
    private String certainty;
    private String event;
    private String note;
    private String effective;
    private String expires;
    private String desc;
    private String instruction;
}
