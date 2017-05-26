package com.rain.learn.algorithm.exception;

public class InvalidInputException extends Exception {

    private static final long serialVersionUID = 526163152252937659L;

    public InvalidInputException() {
        super("Invalid input!");
    }

    public InvalidInputException(String message) {
        super(message);
    }
}
