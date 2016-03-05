package org.struckture.base;

import org.struckture.base.exceptions.StrucktureReadException;

import java.io.InputStream;

/**
 * Struck interface.
 * @param <T> the type of structures to read.
 *
 * The structure type must be annotated with {@link org.struckture.base.annotations.Struckture} annotation.
 */
public interface Struck<T> {
    /**
     * Reads a structure from a stream.
     * @param stream the stream to read from.
     * @return the structure.
     * @throws StrucktureReadException if the structure cannot be read.
     */
    T read(InputStream stream) throws StrucktureReadException;
}
