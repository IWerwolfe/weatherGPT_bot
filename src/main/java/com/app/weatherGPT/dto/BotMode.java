package com.app.weatherGPT.dto;    /*
 *created by WerWolfe on
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BotMode {

    NORMAL("Обычный"),
    PROFANE("Матерный"),
    CREATIVE("Творческий");

    private final String label;

    @Override
    public String toString() {
        return getLabel();
    }
}
