package com.app.weatherGPT.dto;    /*
 *created by WerWolfe on
 */

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BotMode implements EnumDesc {

    NORMAL("Обычный"),
    PROFANE("Матерный"),
    CREATIVE("Творческий");

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
