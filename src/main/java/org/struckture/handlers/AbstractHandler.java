package org.struckture.handlers;

import org.struckture.base.Handler;
import org.struckture.base.annotations.StruckField;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Abstract Handler.
 * @param <T> Type handled by the handler.
 */
public abstract class AbstractHandler<T> implements Handler<T> {
    private Set<Annotation> annotations;
    private int offset = 0;

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public void init(Set<Annotation> annotations) {
        this.annotations = annotations;
        StruckField struckFieldAnnotation = getAnnotation(StruckField.class);
        if (struckFieldAnnotation != null) {
            offset = struckFieldAnnotation.offset();
        }
    }

    /**
     * Gets the annotation for a field. If field is not annotated return null.
     * @param annotationClass the annotation to get.
     * @param <A> annotationClass
     * @return the annotation on the field.
     */
    protected <A> A getAnnotation(Class<A> annotationClass) {
        for (Annotation annotation : annotations) {
            if (annotationClass.isInstance(annotation)) {
                return annotationClass.cast(annotation);
            }
        }
        return null;
    }
}
