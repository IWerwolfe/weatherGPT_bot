package com.app.weatherGPT.dto.api.weather.enums;    /*
 *created by WerWolfe on CardinalDirection
 */

public enum CardinalDirection {

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

    CardinalDirection(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
