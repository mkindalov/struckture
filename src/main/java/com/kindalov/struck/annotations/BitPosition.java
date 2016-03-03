package com.kindalov.struck.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO comment.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@StruckAnnotation
public @interface BitPosition {
    int value();
}
