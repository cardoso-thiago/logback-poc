package br.com.cardoso.context;

import br.com.cardoso.masking.ConfigMask;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpringMaskConfigContext implements ApplicationContextAware {

    private static final Map<String, ConfigMask> configMaskMap = new HashMap<>();

    public static Map<String, ConfigMask> getMaskConfig() {
        return configMaskMap;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        List<ConfigMask> configMasks = applicationContext.getBeansOfType(ConfigMask.class).values().stream().toList();
        for (ConfigMask configMask : configMasks) {
            configMaskMap.put(configMask.getType(), configMask);
        }
    }
}
