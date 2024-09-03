package com.gaber.medium.Aspect;


import com.gaber.medium.Aspect.Annotation.LoggerApi;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
@ConditionalOnProperty(name = "logging.aspect.enabled", havingValue = "true", matchIfMissing = false)
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("@annotation(loggerApi)")
    public void logBeforeMethod(JoinPoint joinPoint, LoggerApi loggerApi) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        logger.info("Request no método: {} da classe: {} com argumentos: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getSimpleName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "@annotation(loggerApi)", returning = "result")
    public void logAfterReturningMethod(JoinPoint joinPoint, LoggerApi loggerApi, Object result) {
        logger.info("Response do método: {} da classe: {} com resultado: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getSimpleName(),
                result);
    }
}
