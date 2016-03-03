package com.kindalov.struck.exceptions;

/**
 * TODO comment.
 */
public class StruckException extends RuntimeException {
    public StruckException(String message) {
        super(message);
    }

    public StruckException(String message, Throwable cause) {
        super(message, cause);
    }
}
