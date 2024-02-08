package com.app.weatherGPT.dto;    /*
 *created by WerWolfe on
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {

    MALE("Мужчина"),
    FEMALE("Женщина");

    private final String label;

    @Override
    public String toString() {
        return getLabel();
    }
}
