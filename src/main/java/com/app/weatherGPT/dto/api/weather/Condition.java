package com.app.weatherGPT.dto.api.weather;    /*
 *created by WerWolfe on Condition
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Condition implements Serializable {

    private String text;
    private String icon;
    private int code;
}
