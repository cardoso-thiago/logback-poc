package br.com.cardoso.masking.impl;

import org.springframework.core.env.Environment;

public class CnpjMask extends BaseMask {

    public CnpjMask(Environment environment) {
        super(environment);
    }

    @Override
    public String getType() {
        return "cnpj";
    }

    @Override
    public Object mask(Object value) {
        String cnpj = String.valueOf(value);
        return cnpj.substring(0, 4) + cnpj.substring(4).replaceAll("[0-9]", "X");
    }
}
