package com.app.weatherGPT.dto.api.weather.enums;    /*
 *created by WerWolfe on UVIndex
 */

import com.app.weatherGPT.dto.EnumDesc;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UVIndex implements EnumDesc {

    LOW("Низкое"),
    MEDIUM("Умеренное"),
    HIGH("Высокое"),
    VERY_HIGH("Очень высокое"),
    EXTREME("Экстремальное");

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
