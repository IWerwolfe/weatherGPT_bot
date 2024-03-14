package com.app.weatherGPT.dto.api.weather.enums;    /*
 *created by WerWolfe on CardinalDirection
 */

import com.app.weatherGPT.dto.EnumDesc;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CardinalDirection implements EnumDesc {

    N("Север"),
    NNE("Северо-северо-восток"),
    NE("Северо-восток"),
    ENE("Восток-северо-восток"),
    E("Восток"),
    ESE("Восток-юго-восток"),
    SE("Юго-восток"),
    SSE("Юго-юго-восток"),
    S("Юг"),
    SSW("Юго-юго-запад"),
    SW("Юго-запад"),
    WSW("Запад-юго-запад"),
    W("Запад"),
    WNW("Запад-северо-запад"),
    NW("Северо-запад"),
    NNW("Северо-северо-запад");

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
