package br.com.cardoso.decorator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.filter.FilteringGeneratorDelegate;
import com.fasterxml.jackson.core.filter.TokenFilter;
import com.fasterxml.jackson.core.filter.TokenFilter.Inclusion;
import net.logstash.logback.decorate.JsonGeneratorDecorator;
import net.logstash.logback.util.StringUtils;

public class OmitEmptyFieldJsonDecorator implements JsonGeneratorDecorator {

    @Override
    public JsonGenerator decorate(JsonGenerator generator) {
        return new FilteringGeneratorDelegate(generator, NullExcludingTokenFilter.INSTANCE, Inclusion.INCLUDE_ALL_AND_PATH, true);
    }

    private static class NullExcludingTokenFilter extends TokenFilter {
        private static final NullExcludingTokenFilter INSTANCE = new NullExcludingTokenFilter();

        @Override
        public boolean includeNull() {
            return false;
        }

        @Override
        public boolean includeString(String value) {
            return !StringUtils.isEmpty(value);
        }
    }
}