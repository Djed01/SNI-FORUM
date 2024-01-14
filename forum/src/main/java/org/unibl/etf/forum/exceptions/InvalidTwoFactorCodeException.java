package org.unibl.etf.forum.exceptions;

public class InvalidTwoFactorCodeException extends RuntimeException{
    public InvalidTwoFactorCodeException() {
        super();
    }

    public InvalidTwoFactorCodeException(String message) {
        super(message);
    }
}
