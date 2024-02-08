package com.app.weatherGPT.model.history;    /*
 *created by WerWolfe on History
 */

import com.app.weatherGPT.model.BotUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private BotUser user;
    private LocalDateTime date;

}
