package com.app.weatherGPT.utils;    /*
 *created by WerWolfe on ConverterUtil
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Slf4j
@Component
public class ConverterUtil {

    private static final double kmPerHourToMetersPerSec = 0.27778;
    private static final String regexNumber = "^[-+]?\\d+(\\.\\d+)?$";
    private static final DecimalFormat decimalFormat = new DecimalFormat();

    public static String formatToString(double value, String pattern) {
        decimalFormat.applyPattern(pattern);
        return decimalFormat.format(value);
    }

    public static int getIntOf(String value) {

        return isCorrectNumb(value) ?
                Integer.parseInt(value) :
                0;
    }

    public static double convertKmPerHourToMetersPerSecond(double kmPerHour) {
        return kmPerHour * kmPerHourToMetersPerSec;
    }

    public static double convertKmPerHourToMetersPerSecond(String kmPerHour) {

        return isCorrectNumb(kmPerHour) ?
                convertKmPerHourToMetersPerSecond(kmPerHourToMetersPerSec) :
                0;
    }

    private static boolean isCorrectNumb(String value) {
        return value != null && !value.isEmpty() && value.matches(regexNumber);
    }
}
