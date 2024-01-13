package org.unibl.etf.forum.exceptions;

public class InvalidUsernameException extends RuntimeException {

    public InvalidUsernameException() {
        super();
    }

    public InvalidUsernameException(String message) {
        super(message);
    }

}