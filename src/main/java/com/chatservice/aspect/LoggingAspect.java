package com.chatservice.aspect;

/**
 * Author: harjeevansingh
 */

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.chatservice..*.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        StopWatch stopWatch = new StopWatch();

        log.info("Starting execution of {}.{}", className, methodName);
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();
        log.info("Execution of {}.{} completed in {}ms", className, methodName, stopWatch.getTotalTimeMillis());

        return result;
    }
}