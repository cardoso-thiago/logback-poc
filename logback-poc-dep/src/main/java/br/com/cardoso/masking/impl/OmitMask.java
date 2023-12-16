package br.com.cardoso.masking.impl;

import org.springframework.core.env.Environment;

public class OmitMask extends BaseMask {
    public OmitMask(Environment environment) {
        super(environment);
    }

    @Override
    public String getType() {
        return "omit";
    }

    @Override
    public Object mask(Object value) {
        return "";
    }
}
