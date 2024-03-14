package com.app.weatherGPT.dto.api.weather.enums;

import com.app.weatherGPT.dto.EnumDesc;
import lombok.AllArgsConstructor;

//@Getter
@AllArgsConstructor
public enum LunarPhase implements EnumDesc {

    NEW_MOON("Новолуние"),
    WAXING_CRESCENT("Молодая луна"),
    FIRST_QUARTER("Первая четверть"),
    WAXING_GIBBOUS("Растущая луна"),
    FULL_MOON("Полнолуние"),
    WANING_GIBBOUS("Убывающая луна"),
    LAST_QUARTER("Последняя четверть"),
    WANING_CRESCENT("Старая Луна");

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
