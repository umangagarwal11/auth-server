package com.example.authserver.exception;

public class TechnicalException extends RuntimeException {
    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(){
        super();
    }

    public TechnicalException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public TechnicalException(Throwable throwable){
        super(throwable);
    }
}
