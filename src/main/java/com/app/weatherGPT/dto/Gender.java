package com.app.weatherGPT.dto;    /*
 *created by WerWolfe on
 */

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Gender implements EnumDesc {

    MALE("Мужчина"),
    FEMALE("Женщина");

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
