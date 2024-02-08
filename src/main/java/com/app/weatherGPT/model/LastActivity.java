package com.app.weatherGPT.model;    /*
 *created by WerWolfe on LastActivity
 */

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "last_activity")
public class LastActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private BotUser user;
    private LocalDateTime date;

}
