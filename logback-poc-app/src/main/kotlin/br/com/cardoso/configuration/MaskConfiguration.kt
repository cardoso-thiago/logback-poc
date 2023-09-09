package br.com.cardoso.configuration

import br.com.cardoso.masking.MyCustomMask
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment


@Configuration
class MaskConfiguration {
    @Bean
    fun myCustomMask(environment: Environment): MyCustomMask {
        return MyCustomMask(environment)
    }
}
