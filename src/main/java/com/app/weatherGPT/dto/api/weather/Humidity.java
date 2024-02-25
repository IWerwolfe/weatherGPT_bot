package com.app.weatherGPT.dto.api.weather;    /*
 *created by WerWolfe on Humidity
 */

public enum Humidity {

    LOW("Очень сухой воздух"),
    MEDIUM("Нормальная влажность"),
    HIGH("Повышенная влажность"),
    VERY_HIGH("Очень высокая влажность");

    private final String label;

    Humidity(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
