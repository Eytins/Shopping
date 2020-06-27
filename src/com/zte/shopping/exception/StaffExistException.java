package com.zte.shopping.exception;

/**
 * Created by Eytins
 */

public class StaffExistException extends Exception {
    private static final long serialVersionUID = 1L;

    public StaffExistException() {

    }

    public StaffExistException(String message) {
        super(message);
    }

    public StaffExistException(Throwable cause) {
        super(cause);
    }

    public StaffExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
