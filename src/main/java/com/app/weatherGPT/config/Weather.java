package com.app.weatherGPT.config;    /*
 *created by WerWolfe on Weather
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "weather")
public class Weather {

    private String urlCurrent;
    private String urlSearch;
    private String urlForecast;
    private String urlAstrology;
    private String token;
    private String lang;
}
