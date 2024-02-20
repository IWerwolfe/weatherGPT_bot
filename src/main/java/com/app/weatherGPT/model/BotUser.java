package com.app.weatherGPT.model;    /*
 *created by WerWolfe on BotUser
 */

import com.app.weatherGPT.dto.BotMode;
import com.app.weatherGPT.dto.Gender;
import com.app.weatherGPT.dto.Lang;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "bot_users")
public class BotUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_name")
    private String userName;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "lang_code")
    @Enumerated(EnumType.STRING)
    private Lang language_code;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "is_valid")
    private Boolean isValid;
    @Column(name = "is_premium")
    private Boolean isPremium;
    @Column(name = "is_bot")
    private Boolean isBot;
    @Column(name = "is_group")
    private Boolean isGroup;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @Column(name = "bot_mode")
    @Enumerated(EnumType.STRING)
    private BotMode botMode;

}
