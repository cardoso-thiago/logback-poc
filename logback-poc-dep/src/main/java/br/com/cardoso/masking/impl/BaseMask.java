package br.com.cardoso.masking.impl;

import br.com.cardoso.masking.ConfigMask;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BaseMask implements ConfigMask {

    private final Environment environment;

    public BaseMask(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public List<String> configMask() {
        String property = environment.getProperty("config.mask." + getType());
        if (property != null) {
            return Arrays.stream(property.split(",")).toList();
        }
        return Collections.emptyList();
    }

    @Override
    public Object mask(Object value) {
        return "***";
    }
}
