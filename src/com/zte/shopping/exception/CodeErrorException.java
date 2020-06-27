package com.zte.shopping.exception;

/**
 * Created by Eytins
 */

public class CodeErrorException extends Exception {
    private static final long serialVersionUID = 1L;

    public CodeErrorException() {

    }

    public CodeErrorException(String message) {
        super(message);
    }

    public CodeErrorException(Throwable cause) {
        super(cause);
    }

    public CodeErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
