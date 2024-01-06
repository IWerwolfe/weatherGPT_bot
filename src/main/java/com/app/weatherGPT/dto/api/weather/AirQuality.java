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

    private double co;
    private double no2;
    private double o3;
    private double so2;
    private double pm2_5;
    private double pm10;
    private double us_epa_index;
    private double gb_defra_index;
}
