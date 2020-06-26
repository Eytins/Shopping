package com.zte.shopping.exception;

/**
 * Created by Eytins
 */

public class RequestParameterException extends Exception {
    private static final long serialVersionUID = 1L;

    public RequestParameterException() {

    }

    public RequestParameterException(String message) {
        super(message);
    }

    public RequestParameterException(Throwable cause) {
        super(cause);
    }

    public RequestParameterException(String message, Throwable cause) {
        super(message, cause);
    }

}
