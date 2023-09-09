package br.com.cardoso.configuration;

import br.com.cardoso.context.SpringMaskConfigContext;
import br.com.cardoso.masking.impl.CnpjMask;
import br.com.cardoso.masking.impl.CpfMask;
import br.com.cardoso.masking.impl.HiddenMask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class LogConfiguration {

    @Bean
    public CpfMask cpfMask(Environment environment) {
        return new CpfMask(environment);
    }

    @Bean
    public CnpjMask cnpjMask(Environment environment) {
        return new CnpjMask(environment);
    }

    @Bean
    public HiddenMask hiddenMask(Environment environment) {
        return new HiddenMask(environment);
    }

    @Bean
    public SpringMaskConfigContext springMaskConfigContext() {
        return new SpringMaskConfigContext();
    }
}
