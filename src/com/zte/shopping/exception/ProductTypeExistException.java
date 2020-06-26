package com.zte.shopping.exception;

/**
 * Created by Eytins
 */

public class ProductTypeExistException extends Exception {
    private static final long serialVersionUID = 1L;

    public ProductTypeExistException() {

    }

    public ProductTypeExistException(String message) {
        super(message);
    }

    public ProductTypeExistException(Throwable cause) {
        super(cause);
    }

    public ProductTypeExistException(String message, Throwable cause) {
        super(message, cause);
    }

}