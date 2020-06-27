package com.zte.shopping.exception;

/**
 * Created by Eytins
 */

public class NoPromissionException extends Exception {
    private static final long serialVersionUID = 1L;

    public NoPromissionException() {

    }

    public NoPromissionException(String message) {
        super(message);
    }

    public NoPromissionException(Throwable cause) {
        super(cause);
    }

    public NoPromissionException(String message, Throwable cause) {
        super(message, cause);
    }

}
