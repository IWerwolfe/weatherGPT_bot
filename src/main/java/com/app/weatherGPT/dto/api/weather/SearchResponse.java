package com.app.weatherGPT.dto.api.weather;    /*
 *created by WerWolfe on WeatherResponse
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse implements Serializable {

    private List<Location> locations;
}
