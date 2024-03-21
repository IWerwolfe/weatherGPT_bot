package com.app.weatherGPT.model;    /*
 *created by WerWolfe on Subscription
 */

import com.app.weatherGPT.dto.Frequency;
import com.app.weatherGPT.model.location.City;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TimeZone;

@Getter
@Setter
@Entity
@Table(name = "subscriptions")
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private BotUser user;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @Enumerated(EnumType.STRING)
    private Frequency frequency;
    private LocalDateTime lastSend;
    private LocalTime localTime;

}
