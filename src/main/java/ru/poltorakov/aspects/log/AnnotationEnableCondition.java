package ru.poltorakov.aspects.log;

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;

public class AnnotationEnableCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return findAnnotatedClasses(EnableLog.class, "ru.poltorakov");
    }
    public boolean findAnnotatedClasses(Class<? extends Annotation> annotationType, String packagesToBeScanned)
    {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new
                AnnotationTypeFilter(annotationType));
        return !provider.findCandidateComponents(packagesToBeScanned).isEmpty();
    }
}
