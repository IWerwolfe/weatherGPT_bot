package com.app.weatherGPT.dto.api.weather;

public enum UsEpaIndex {

    GOOD("Хорошее"),
    MODERATE("Умеренное"),
    UNHEALTHY_SENSITIVE("Вредное для чувствительных групп"),
    UNHEALTHY("Вредное"),
    VERY_UNHEALTHY("Очень вредное"),
    HAZARDOUS("Опасное");

    private final String label;

    UsEpaIndex(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
