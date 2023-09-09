package br.com.cardoso.configuration;

import br.com.cardoso.context.SpringEnvironmentContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfiguration {

    @Bean
    public SpringEnvironmentContext springContext() {
        return new SpringEnvironmentContext();
    }
}
