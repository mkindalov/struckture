package org.struckture.base;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Handler. Knows to convert array of bytes to object of type T.
 * Should have
 * @param <T> the return type of the handler.
 */
public interface Handler<T> {
    /**
     * Called after the Handler creation.
     * @param annotations the field annotations.
     */
    void init(Set<Annotation> annotations);

    /**
     * Gets the size in bytes.
     * @return the size.
     */
    int getSize();

    /**
     * Gets tje offset.
     * @return the offset.
     */
    int getOffset();

    /**
     * Gets the value from the byte array.
     * @param data the byte array
     * @return the value.
     */
    T getValue(byte[] data);
}
