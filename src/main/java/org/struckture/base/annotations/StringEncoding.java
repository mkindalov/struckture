package org.struckture.base.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sets a encoding for handlers.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@StrucktureAnnotation
public @interface StringEncoding {
    /**
     * Encoding name.
     * @return string encoding
     */
    String value();
}
