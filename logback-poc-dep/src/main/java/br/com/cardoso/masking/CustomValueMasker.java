package br.com.cardoso.masking;

import br.com.cardoso.context.SpringMaskConfigContext;
import com.fasterxml.jackson.core.JsonStreamContext;
import net.logstash.logback.mask.ValueMasker;

import java.util.Map;

public class CustomValueMasker implements ValueMasker {

    public static final String TOKEN_SEPARATOR = "/";
    public static final String WILDCARD_TOKEN = "*";
    private Map<String, ConfigMask> maskConfigMap;

    private boolean currentLeafMatches(JsonStreamContext context, String leafName) {
        if (context != null) {
            if (WILDCARD_TOKEN.equals(leafName)) {
                return true;
            }
            if (context.hasCurrentName()) {
                return context.getCurrentName().equals(leafName);
            }
            if (context.hasCurrentIndex()) {
                return Integer.toString(context.getCurrentIndex()).equals(leafName);
            }
        }
        return false;
    }

    private String unescapeJsonPointerToken(String token) {
        return token.replace("~1", "/").replace("~0", "~");
    }

    @Override
    public Object mask(JsonStreamContext context, Object value) {
        populateContext();
        if (maskConfigMap != null && maskConfigMap.size() > 0) {
            for (String key : maskConfigMap.keySet()) {
                for (String configMaskValue : maskConfigMap.get(key).configMask()) {
                    if (shouldMask(context, configMaskValue)) {
                        return maskConfigMap.get(key).mask(value);
                    }
                }
            }
        }
        return null;
    }

    private boolean shouldMask(JsonStreamContext context, String pathToMask) {
        if (pathToMask != null) {
            boolean isAbsolutePath = pathToMask.startsWith(TOKEN_SEPARATOR);

            if (isAbsolutePath) {
                pathToMask = pathToMask.substring(TOKEN_SEPARATOR.length());
            }
            String[] tokens = pathToMask.split(TOKEN_SEPARATOR);

            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = unescapeJsonPointerToken(tokens[i]);
            }
            JsonStreamContext currentContext = context;
            for (int i = tokens.length; --i >= 0; currentContext = currentContext.getParent()) {
                if (!currentLeafMatches(currentContext, tokens[i])) {
                    return false;
                }
            }
            return currentContext != null && (!isAbsolutePath || currentContext.inRoot());
        }
        return false;
    }

    private void populateContext() {
        if (maskConfigMap == null) {
            maskConfigMap = SpringMaskConfigContext.getMaskConfig();
        }
    }
}
