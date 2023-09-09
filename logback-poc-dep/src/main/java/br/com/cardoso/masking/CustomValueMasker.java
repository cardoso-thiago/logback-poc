package br.com.cardoso.masking;

import br.com.cardoso.context.SpringEnvironmentContext;
import com.fasterxml.jackson.core.JsonStreamContext;
import net.logstash.logback.mask.ValueMasker;
import org.springframework.core.env.Environment;

public class CustomValueMasker implements ValueMasker {

    public static final String TOKEN_SEPARATOR = "/";
    public static final String WILDCARD_TOKEN = "*";

    private boolean isAbsolutePath;
    private String[] tokens;
    private Object mask;

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
        if (getEnvironment() != null && tokens != null) {
            JsonStreamContext currentContext = context;
            for (int i = tokens.length; --i >= 0; currentContext = currentContext.getParent()) {
                if (!currentLeafMatches(currentContext, tokens[i])) {
                    return null;
                }
            }
            return (currentContext != null && (!isAbsolutePath || currentContext.inRoot())) ? mask : null;
        }
        return null;
    }

    private Environment getEnvironment() {
        Environment environment = SpringEnvironmentContext.getEnvironment();
        if (environment != null) {
            if (tokens == null) {
                String pathToMask = environment.getProperty("config.path.to.mask");
                if (pathToMask != null) {
                    isAbsolutePath = pathToMask.startsWith(TOKEN_SEPARATOR);

                    if (isAbsolutePath) {
                        pathToMask = pathToMask.substring(TOKEN_SEPARATOR.length());
                    }
                    tokens = pathToMask.split(TOKEN_SEPARATOR);

                    for (int i = 0; i < tokens.length; i++) {
                        tokens[i] = unescapeJsonPointerToken(tokens[i]);
                    }
                    this.mask = getEnvironment().getProperty("config.mask");
                }
            }
        }
        return environment;
    }
}
