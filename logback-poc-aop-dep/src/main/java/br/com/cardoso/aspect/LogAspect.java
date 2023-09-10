package br.com.cardoso.aspect;

import net.logstash.logback.marker.Markers;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;

@Aspect
public class LogAspect {

    @Around("@within(br.com.cardoso.annotation.LogAop)")
    public Object logAop(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        Signature signature = joinPoint.getSignature();
        String method = signature.getName();
        LoggerFactory.getLogger(signature.getDeclaringType()).info(Markers.append("method", method), "");
        return proceed;
    }
}
