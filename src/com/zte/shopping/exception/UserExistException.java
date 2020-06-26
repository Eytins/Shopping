package com.zte.shopping.exception;

/**
 * Created by Eytins
 */

public class UserExistException extends Exception {

    private static final long serialVersionUID = 1L;

    public UserExistException() {

    }

    public UserExistException(String message) {
        super(message);
    }

    public UserExistException(Throwable cause) {
        super(cause);
    }

    public UserExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
