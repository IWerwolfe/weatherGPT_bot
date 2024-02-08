package com.app.weatherGPT.model.history;    /*
 *created by WerWolfe on HistoryCity
 */


import com.app.weatherGPT.model.City;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "history_cities")
public class HistoryCity extends History {

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

}
