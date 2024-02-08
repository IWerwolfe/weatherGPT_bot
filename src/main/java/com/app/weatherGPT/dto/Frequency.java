package com.app.weatherGPT.dto;    /*
 *created by WerWolfe on Frequency
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Frequency {

    DAILY("Ежедневно"),
    EVERY_3_DAYS("Раз в 3 дня"),
    WEEKLY("Раз в неделю");

    private final String label;

    @Override
    public String toString() {
        return getLabel();
    }
}
