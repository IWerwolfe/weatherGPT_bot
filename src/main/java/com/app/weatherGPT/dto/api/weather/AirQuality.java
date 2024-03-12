package com.app.weatherGPT.dto.api.weather;    /*
 *created by WerWolfe on AirQuality
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirQuality implements Serializable {

    private float co;
    private float no2;
    private float o3;
    private float so2;
    private float pm2_5;
    private float pm10;
    private int us_epa_index;
    private int gb_defra_index;
}
