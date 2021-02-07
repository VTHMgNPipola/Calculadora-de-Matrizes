package com.vthmgnpipola.matrixcalc.calc;

public class CalculadoraException extends RuntimeException {
    public CalculadoraException() {
    }

    public CalculadoraException(String message) {
        super(message);
    }

    public CalculadoraException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalculadoraException(Throwable cause) {
        super(cause);
    }

    public CalculadoraException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
