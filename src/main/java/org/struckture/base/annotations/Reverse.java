package org.struckture.base.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TIndicates that instead of bigEndian little endian should be used.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@StrucktureAnnotation
public @interface Reverse {
}
