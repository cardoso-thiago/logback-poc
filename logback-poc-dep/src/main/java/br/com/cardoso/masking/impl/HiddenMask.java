package br.com.cardoso.masking.impl;

import org.springframework.core.env.Environment;

public class HiddenMask extends BaseMask {

    public HiddenMask(Environment environment) {
        super(environment);
    }

    @Override
    public String getType() {
        return "hidden";
    }

    @Override
    public Object mask(Object value) {
        return "<campo_ofuscado>";
    }
}
