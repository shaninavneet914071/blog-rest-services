package com.alok.blogger.blogapp.exceptions;

public class ApiException extends RuntimeException {
    /**
     * @param message
     */
    public ApiException(String message) {
        super(message);
    }

    public ApiException() {
        super();
    }
}
