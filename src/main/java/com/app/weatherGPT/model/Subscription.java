package com.app.weatherGPT.model;    /*
 *created by WerWolfe on Subscription
 */

import com.app.weatherGPT.dto.Frequency;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
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
    private Frequency frequency;
    private LocalDateTime lastSend;

}
