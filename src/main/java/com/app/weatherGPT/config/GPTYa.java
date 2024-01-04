package com.app.weatherGPT.config;    /*
 *created by WerWolfe on
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GPTYa {
    private String url;
    private String token;
    private boolean stream;
    private int maxToken;
    private float temperature;
    private String model;
}
