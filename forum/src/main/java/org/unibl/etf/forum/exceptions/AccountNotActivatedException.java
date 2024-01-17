package org.unibl.etf.forum.exceptions;

public class AccountNotActivatedException extends  Exception{
    public AccountNotActivatedException() {
        super();
    }

    public AccountNotActivatedException(String message) {
        super(message);
    }
}
