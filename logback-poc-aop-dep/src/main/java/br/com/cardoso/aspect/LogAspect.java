package br.com.cardoso.aspect;

import net.logstash.logback.marker.Markers;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class LogAspect {

    @Pointcut("@annotation(br.com.cardoso.annotation.LogAop) || @within(br.com.cardoso.annotation.LogAop)")
    public void isAnnotatedWithLogAop() {
    }

    @Around("isAnnotatedWithLogAop()")
    public Object logAop(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed;
        try {
            proceed = joinPoint.proceed();
        } catch (Exception e) {
            autoLog(joinPoint, startTime, e);
            throw e;
        }
        autoLog(joinPoint, startTime, null);
        return proceed;
    }

    private static void autoLog(ProceedingJoinPoint joinPoint, long startTime, Exception ex) {
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        Map<String, String> info = new HashMap<>();
        info.put("execution_time", executionTime + "ms");

        AnnotationAttributes requestAnnotationAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(
                ((MethodSignature) joinPoint.getSignature()).getMethod(),
                RequestMapping.class
        );
        if (requestAnnotationAttributes != null) {
            String uri = String.join(",", (String[]) requestAnnotationAttributes.get("value"));
            info.put("uri", uri);
        }

        Signature signature = joinPoint.getSignature();
        String method = signature.getName();
        info.put("method", method);

        if (ex == null) {
            LoggerFactory.getLogger(signature.getDeclaringType()).info(Markers.appendEntries(info), "");
        } else {
            LoggerFactory.getLogger(signature.getDeclaringType()).error(Markers.appendEntries(info), "", ex);
        }
    }
}
