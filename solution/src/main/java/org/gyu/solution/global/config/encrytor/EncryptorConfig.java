package org.gyu.solution.global.config.encrytor;

import org.springframework.core.env.Environment;
import org.gyu.solution.global.base.Encryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptorConfig {
    @Bean
    public Encryptor encryptor(Environment env) {
        return new Encryptor(env);
    }
}
