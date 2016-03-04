package org.struckture.base.exceptions;

/**
 * Field Configuration Exception.
 */
public class FieldConfigurationException extends StruckException {
    /**
     * Constructor.
     * @param message message
     */
    public FieldConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message message
     * @param cause cause
     */
    public FieldConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
