package org.struckture.base.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a Structure(Class) can be used for reading with Struckture. <br/>
 * Length attribute sets the length of the structure. <br/>
 * If more than one field depends on a same byte than one must allowOverlapping
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Struckture {

    /**
     * The length of the struckture.
     * @return length;
     */
    int length();

    /**
     * Allows overlapping of fields.
     * @return if overlapping is allowed;
     */
    boolean allowOverlapping() default false;
}
