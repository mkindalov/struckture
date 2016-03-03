package com.kindalov.struck.handlers;

import com.kindalov.struck.Handler;
import com.kindalov.struck.annotations.StruckField;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * TODO comment.
 */
public abstract class AbstractHandler<T> implements Handler<T> {
    private Set<Annotation> annotations;
    //TODO check if needed
    private StruckField struckFieldAnnotation;
    private int offset = 0;

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public void init(Set<Annotation> annotations) {
        this.annotations = annotations;
        struckFieldAnnotation =  getAnnotation(StruckField.class);
        if (struckFieldAnnotation != null) {
            offset = struckFieldAnnotation.offset();
        }
    }

    protected <T> T getAnnotation(Class<T> annotationClass) {
        for (Annotation annotation : annotations) {
            if (annotationClass.isInstance(annotation)) {
                return annotationClass.cast(annotation);
            }
        }
        return null;
    }
}
