package com.zte.shopping.exception;

/**
 * Created by Eytins
 */


public class DeptExistException extends Exception {

    private static final long serialVersionUID = 1L;

    public DeptExistException() {
        super();
    }

    public DeptExistException(String message, Throwable cause,
                              boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DeptExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeptExistException(String message) {
        super(message);
    }

    public DeptExistException(Throwable cause) {
        super(cause);
    }

}
