package br.com.cardoso.masking.impl;

import org.springframework.core.env.Environment;

public class CpfMask extends BaseMask {

    public CpfMask(Environment environment) {
        super(environment);
    }

    @Override
    public String getType() {
        return "cpf";
    }

    @Override
    public Object mask(Object value) {
        String cpf = String.valueOf(value);
        return cpf.substring(0, 3) + cpf.substring(3).replaceAll("[0-9]", "X");
    }
}
