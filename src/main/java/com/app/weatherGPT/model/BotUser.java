package com.app.weatherGPT.model;    /*
 *created by WerWolfe on BotUser
 */

import com.app.weatherGPT.dto.BotMode;
import com.app.weatherGPT.dto.Gender;
import com.app.weatherGPT.dto.Lang;
import com.app.weatherGPT.model.location.UserLocation;
import com.app.weatherGPT.utils.ConverterUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.Clock;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
//@NoArgsConstructor
@Table(name = "bot_users")
public class BotUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "telegram_id")
    private Long telegramId;
    @Column(name = "user_name")
    private String userName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "lang_code")
    @Enumerated(EnumType.STRING)
    private Lang language_code;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "is_premium")
    private Boolean isPremium;
    @Column(name = "is_bot")
    private Boolean isBot;
    private UserLocation location;
    @Column(name = "bot_mode")
    @Enumerated(EnumType.STRING)
    private BotMode botMode;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "last_activity")
    private LastActivity lastActivity;

    public BotUser() {
        this.isDeleted = false;
        this.botMode = BotMode.NORMAL;
        this.language_code = Lang.RU;
        this.userName = "No name bot user " + id;
        this.lastActivity = new LastActivity(this);
        this.location = UserLocation.getDefaultLocation();
    }

    public BotUser(User user) {
        this();
        BeanUtils.copyProperties(user, this, "id");
        this.telegramId = user.getId();
        this.language_code = ConverterUtil.convertToEnum(user.getLanguageCode().toUpperCase(), Lang.class);
    }

    public BotUser(long id) {
        this();
        this.telegramId = id;
    }

    public void updateLastActivity() {
        lastActivity.setDate(LocalDateTime.now(Clock.systemDefaultZone()));
    }
}
