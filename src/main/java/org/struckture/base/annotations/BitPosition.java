package org.struckture.base.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies a bit position if needed for the handler.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@StrucktureAnnotation
public @interface BitPosition {
    /**
     * The bit position. Value between 0-7 is expected.
     * @return position
     */
    int value();
}
