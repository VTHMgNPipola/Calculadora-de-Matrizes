package com.vthmgnpipola.matrixcalc.comandos;

public class ComandoException extends Exception {
    public ComandoException() {
    }

    public ComandoException(String message) {
        super(message);
    }

    public ComandoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComandoException(Throwable cause) {
        super(cause);
    }

    public ComandoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
