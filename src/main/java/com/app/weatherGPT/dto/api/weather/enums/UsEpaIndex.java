package com.app.weatherGPT.dto.api.weather.enums;

import com.app.weatherGPT.dto.EnumDesc;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UsEpaIndex implements EnumDesc {

    GOOD("Хорошее"),
    MODERATE("Умеренное"),
    UNHEALTHY_SENSITIVE("Вредное для чувствительных групп"),
    UNHEALTHY("Вредное"),
    VERY_UNHEALTHY("Очень вредное"),
    HAZARDOUS("Опасное");

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
