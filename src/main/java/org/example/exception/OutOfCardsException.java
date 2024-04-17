package org.example.exception;

public class OutOfCardsException extends Exception {

    private static final long serialVersionUID = 1L;

    public OutOfCardsException(String message) {
        super(message);
    }

}