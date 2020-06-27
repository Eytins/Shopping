package com.zte.shopping.exception;

/**
 * Created by Eytins
 */

public class LoginNameOrPasswordErrorException extends Exception {
    private static final long serialVersionUID = 1L;

    public LoginNameOrPasswordErrorException() {

    }

    public LoginNameOrPasswordErrorException(String message) {
        super(message);
    }

    public LoginNameOrPasswordErrorException(Throwable cause) {
        super(cause);
    }

    public LoginNameOrPasswordErrorException(String message, Throwable cause) {
        super(message, cause);
    }

}
