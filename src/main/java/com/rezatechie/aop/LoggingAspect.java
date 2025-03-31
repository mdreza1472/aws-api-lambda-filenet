package com.rezatechie.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // Apply to all controller and service methods
    @Around("execution(* com.rezatechie..controller..*(..)) || execution(* com.rezatechie..service..*(..))")
    public Object logMethodCalls(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringType().getSimpleName() + "." + signature.getName();

        Object[] args = joinPoint.getArgs();
        log.info("➡️ Entering {} with arguments: {}", methodName, Arrays.toString(args));

        Object result = joinPoint.proceed();

        log.info("⬅️ Exiting {} with return value: {}", methodName, result);
        return result;
    }
}
