package br.com.cardoso.provider;

import ch.qos.logback.core.spi.DeferredProcessingAware;
import com.fasterxml.jackson.core.JsonGenerator;
import net.logstash.logback.composite.AbstractFieldJsonProvider;
import net.logstash.logback.composite.JsonWritingUtils;

import java.io.IOException;
import java.util.UUID;

public class CustomProvider<Event extends DeferredProcessingAware> extends AbstractFieldJsonProvider<Event> {

    public static final String FIELD_CUSTOM = "custom";
    public static final String STRATEGY_RANDOM = "random";
    public static final String STRATEGY_FIXED = "fixed";

    public String strategy = STRATEGY_FIXED;

    public CustomProvider() {
        setFieldName(FIELD_CUSTOM);
    }

    @Override
    public void writeTo(JsonGenerator generator, Event event) throws IOException {
        if (STRATEGY_RANDOM.equals(getStrategy())) {
            JsonWritingUtils.writeStringField(generator, getFieldName(), UUID.randomUUID().toString());
        } else {
            JsonWritingUtils.writeStringField(generator, getFieldName(), "Valor fixo!");
        }
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }
}