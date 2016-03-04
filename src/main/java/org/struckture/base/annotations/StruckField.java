package org.struckture.base.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specified a field in a Struckture. If a class field is not annotated then it is not processed by Struckture.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@StrucktureAnnotation
public @interface StruckField {

    /**
     * Offset from the beginning of the binary data.
     * @return offset
     */
    int offset();

    /**
     * Size used in case of arrays.
     * @return size
     */
    int size() default 0;
}
