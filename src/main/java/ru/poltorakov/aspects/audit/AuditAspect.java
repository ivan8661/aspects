package ru.poltorakov.aspects.audit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.poltorakov.domain.model.useraction.Action;
import ru.poltorakov.domain.model.useraction.UserAction;
import ru.poltorakov.domain.model.users.User;
import ru.poltorakov.domain.service.UserActionService;

import java.time.ZonedDateTime;

/**
 * The {@code AuditAspect} class is an aspect for auditing user actions in the application.
 * It captures and logs various user actions, such as credits, debits, authorizations, registrations, and balance checks.
 * This aspect is triggered after methods annotated with {@link Audit} are successfully executed.
 * The captured user actions are then stored in the database for auditing purposes.
 *
 * @author ivan@poltorakov.ru
 * @version 1.0.0
 */
@Aspect
@Component
public class AuditAspect {

    private final UserActionService userActionService;

    public AuditAspect(UserActionService userActionService) {
        this.userActionService = userActionService;
    }

    @Pointcut("@annotation(ru.poltorakov.aspects.audit.Audit)")
    public void serviceMethods() {
    }

    @Before("serviceMethods()")
    public void validateToken(JoinPoint joinPoint) throws Throwable {
        User user = (User) joinPoint.getArgs()[0];
        String methodName = joinPoint.getSignature().getName();
        switch (methodName) {
            case "fund" -> userActionService.saveUserAction(user, new UserAction(Action.CREDIT, ZonedDateTime.now(), user.getLogin()));
            case "withdraw" -> userActionService.saveUserAction(user, new UserAction(Action.DEBIT, ZonedDateTime.now(), user.getLogin()));
            case "authenticate" -> userActionService.saveUserAction(user, new UserAction(Action.AUTHORIZATION, ZonedDateTime.now(), user.getLogin()));
            case "registerUser" -> userActionService.saveUserAction(user, new UserAction(Action.REGISTRATION, ZonedDateTime.now(), user.getLogin()));
            case "getBalance" -> userActionService.saveUserAction(user, new UserAction(Action.BALANCE_CHECK, ZonedDateTime.now(), user.getLogin()));
        }
    }
}
