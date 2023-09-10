package br.com.cardoso.configuration;

import br.com.cardoso.aspect.LogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogAopConfiguration {

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }
}
