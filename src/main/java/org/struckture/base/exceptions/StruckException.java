package org.struckture.base.exceptions;

/**
 * Struck Exception.
 */
public class StruckException extends RuntimeException {

    /**
     * Constructor.
     * @param message message
     */
    public StruckException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message message
     * @param cause cause
     */
    public StruckException(String message, Throwable cause) {
        super(message, cause);
    }
}
