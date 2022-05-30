package com.revature.ggPopcorn.util.custom_exceptions;

public class InvalidSQLException extends RuntimeException {
    public InvalidSQLException(String message) {
        super(message);
    }
}
