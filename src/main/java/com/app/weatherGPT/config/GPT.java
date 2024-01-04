package com.app.weatherGPT.config;    /*
 *created by WerWolfe on GPT
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "gpt")
public class GPT {
    private GPTYa gptYa;

}
