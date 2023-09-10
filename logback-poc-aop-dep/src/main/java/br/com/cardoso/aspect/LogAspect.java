package br.com.cardoso.aspect;

import net.logstash.logback.marker.Markers;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;

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
        Signature signature = joinPoint.getSignature();
        String method = signature.getName();
        Map<String, String> info = new HashMap<>();
        info.put("method", method);
        info.put("execution_time", executionTime + "ms");
        if (ex == null) {
            LoggerFactory.getLogger(signature.getDeclaringType()).info(Markers.appendEntries(info), "");
        } else {
            LoggerFactory.getLogger(signature.getDeclaringType()).error(Markers.appendEntries(info), "", ex);
        }
    }
}
