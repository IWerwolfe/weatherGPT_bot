package com.app.weatherGPT.dto.api.weather.description;    /*
 *created by WerWolfe on Location
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location implements Serializable {

    private String name;
    private String region;
    private String country;
    private double lat;
    private double lon;

    @JsonProperty("tz_id")
    private String tzId;

    @JsonProperty("localtime_epoch")
    private long localtimeEpoch;

    private String localtime;
    private String url;
}
