package com.app.weatherGPT.dto.api.weather.enums;    /*
 *created by WerWolfe on Humidity
 */

import com.app.weatherGPT.dto.EnumDesc;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Humidity implements EnumDesc {

    LOW("Очень сухой воздух"),
    MEDIUM("Нормальная влажность"),
    HIGH("Повышенная влажность"),
    VERY_HIGH("Очень высокая влажность");

    private final String label;

    @Override
    public String toString() {
        return getLabel();
    }

    @Override
    public String getLabel() {
        return this.label;
    }
}
