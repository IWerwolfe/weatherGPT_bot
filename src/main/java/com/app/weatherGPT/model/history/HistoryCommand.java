package com.app.weatherGPT.model.history;    /*
 *created by WerWolfe on HistoryCommand
 */

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "history_commands")
public class HistoryCommand extends History {

    private String command;
    private String text;

}
