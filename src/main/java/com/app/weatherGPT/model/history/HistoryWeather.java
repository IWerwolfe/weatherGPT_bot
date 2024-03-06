package com.app.weatherGPT.model.history;    /*
 *created by WerWolfe on HistoryWeather
 */

import com.app.weatherGPT.model.location.City;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "history_weathers")
public class HistoryWeather extends History {

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    private String descriptor;
    private String prompt;

}
