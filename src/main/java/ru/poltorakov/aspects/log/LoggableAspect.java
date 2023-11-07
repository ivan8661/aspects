package ru.poltorakov.aspects.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import ru.poltorakov.aspects.log.AnnotationEnableCondition;

import java.time.ZonedDateTime;

/**
 * Aspect for logging method execution and measuring execution time.
 *
 * @author ivan@poltorakov.ru
 * @version 1.0.0
 */
@Aspect
@Component
@Conditional(AnnotationEnableCondition.class)
public class LoggableAspect {
    /**
     * Logs method execution start time, execution duration, and finish time.
     *
     * @param joinPoint The join point representing the method execution.
     * @return The result of the method execution.
     * @throws Throwable An exception if an error occurs during method execution.
     */
    @Around("execution(* ru..*(..))")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        ZonedDateTime startTime = ZonedDateTime.now();
        System.out.println("\nBeginning " + joinPoint.getSignature().getName() + " method execution at " + startTime);
        ZonedDateTime endTime = ZonedDateTime.now();
        long executionTimeMillis = endTime.toInstant().toEpochMilli() - startTime.toInstant().toEpochMilli();
        System.out.println("\nMethod " + joinPoint.getSignature().getName() + " finished at " + endTime +  "\nexecution time: " + executionTimeMillis + "ms");
        return joinPoint.proceed();
    }
}
