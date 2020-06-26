package com.zte.shopping.exception;

/**
 * Created by Eytins
 */

public class SatffNotExistException extends Exception {

    private static final long serialVersionUID = 1L;

    public SatffNotExistException() {
        super();
    }

    public SatffNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SatffNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public SatffNotExistException(String message) {
        super(message);
    }

    public SatffNotExistException(Throwable cause) {
        super(cause);
    }

}
