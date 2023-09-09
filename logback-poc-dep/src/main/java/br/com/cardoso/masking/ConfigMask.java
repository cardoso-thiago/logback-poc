package br.com.cardoso.masking;

import java.util.List;

public interface ConfigMask {

    String getType();

    List<String> configMask();

    Object mask(Object value);
}
