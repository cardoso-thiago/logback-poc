package br.com.cardoso.aspect;

import br.com.cardoso.annotation.LogAop;
import net.logstash.logback.marker.Markers;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class LogAspect {

    @Pointcut("(@annotation(br.com.cardoso.annotation.LogAop) || @within(br.com.cardoso.annotation.LogAop)) && " +
            "!@annotation(org.springframework.web.bind.annotation.RequestMapping) &&" +
            "!@annotation(org.springframework.web.bind.annotation.GetMapping) &&" +
            "!@annotation(org.springframework.web.bind.annotation.PostMapping) &&" +
            "!@annotation(org.springframework.web.bind.annotation.PatchMapping) &&" +
            "!@annotation(org.springframework.web.bind.annotation.PutMapping) &&" +
            "!@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void isAnnotatedWithLogAop() {
    }

    @Around("isAnnotatedWithLogAop()")
    public Object logAop(ProceedingJoinPoint joinPoint) throws Throwable {
        return handleLog(joinPoint, null);
    }

    @Around(value = "(@annotation(logAop) && @annotation(requestMapping)) || " +
            "(@within(logAop) && @annotation(requestMapping))", argNames = "logAop,requestMapping")
    public Object logAopRequestMapping(ProceedingJoinPoint joinPoint, LogAop logAop, RequestMapping requestMapping) throws Throwable {
        String uri = String.join(",", requestMapping.value());
        uri = uri + String.join(",", requestMapping.path());
        return handleLog(joinPoint, uri);
    }

    @Around(value = "(@annotation(logAop) && @annotation(getMapping)) || " +
            "(@within(logAop) && @annotation(getMapping))", argNames = "logAop,getMapping")
    public Object logAopGetMapping(ProceedingJoinPoint joinPoint, LogAop logAop, GetMapping getMapping) throws Throwable {
        String uri = String.join(",", getMapping.value());
        uri = uri + String.join(",", getMapping.path());
        return handleLog(joinPoint, uri);
    }

    private Object handleLog(ProceedingJoinPoint joinPoint, String uri) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed;
        try {
            proceed = joinPoint.proceed();
        } catch (Exception e) {
            autoLog(joinPoint, startTime, e, uri);
            throw e;
        }
        autoLog(joinPoint, startTime, null, uri);
        return proceed;
    }

    private void autoLog(ProceedingJoinPoint joinPoint, long startTime, Exception ex, String uri) {
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        Map<String, String> info = new HashMap<>();
        info.put("execution_time", executionTime + "ms");

        if (uri != null) {
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
