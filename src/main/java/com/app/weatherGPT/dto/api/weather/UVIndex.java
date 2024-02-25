package com.app.weatherGPT.dto.api.weather;    /*
 *created by WerWolfe on UVIndex
 */

public enum UVIndex {

    LOW("Низкое"),
    MEDIUM("Умеренное"),
    HIGH("Высокое"),
    VERY_HIGH("Очень высокое"),
    EXTREME("Экстремальное");

    private final String label;

    UVIndex(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
