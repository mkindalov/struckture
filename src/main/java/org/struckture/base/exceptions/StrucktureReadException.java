package org.struckture.base.exceptions;

/**
 * Struckture Read Exception.
 */
public class StrucktureReadException extends StruckException {

    /**
     * Constructor.
     * @param message message
     */
    public StrucktureReadException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message message
     * @param cause cause
     */
    public StrucktureReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
