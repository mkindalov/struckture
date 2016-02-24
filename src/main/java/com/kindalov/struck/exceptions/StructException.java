package com.kindalov.struck.exceptions;

/**
 * TODO comment.
 */
public class StructException extends RuntimeException {
    public StructException(String message) {
        super(message);
    }

    public StructException(String message, Throwable cause) {
        super(message, cause);
    }

    public StructException(Throwable cause) {
        super(cause);
    }
}
