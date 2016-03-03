package com.kindalov.struck;

import com.kindalov.struck.exceptions.StruckReadException;

import java.io.InputStream;

/**
 * Struck interface.
 */
public interface IStruck<T> {
    /**
     * Reads a structure from a stream.
     * @param stream the stream to read from.
     * @return the structure.
     */
    T read(InputStream stream) throws StruckReadException;
}
