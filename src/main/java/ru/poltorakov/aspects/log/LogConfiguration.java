package ru.poltorakov.aspects.log;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Conditional;

@AutoConfiguration
@Conditional(AnnotationEnableCondition.class)
public class LogConfiguration {

}
