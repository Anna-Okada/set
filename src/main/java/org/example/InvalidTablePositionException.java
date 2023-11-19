package org.example;

public class InvalidTablePositionException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidTablePositionException(String message) {
        super(message);
    }

}