package com.app.weatherGPT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherGPTBotApp {

    public static void main(String[] args) {
        SpringApplication.run(WeatherGPTBotApp.class, args);
    }
}
