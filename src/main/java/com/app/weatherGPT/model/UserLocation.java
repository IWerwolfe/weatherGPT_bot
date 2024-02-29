package com.app.weatherGPT.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class UserLocation {

    private Double latitude;
    private Double longitude;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public UserLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UserLocation(City city) {
        this.city = city;
    }

    public String getCityName() {
        return this.city.getName();
    }

    public static UserLocation getDefaultLocation() {
        return new UserLocation(55.7522, 37.6156);
    }
}
