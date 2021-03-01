package com.tp.toneRowMatrixCalculator.exceptions;

public class InvalidIdException extends Exception {
    public InvalidIdException(String msg) {
        super(msg);
    }

    public InvalidIdException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
