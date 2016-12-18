package br.org.cesar.knot.lib;

/**
 * Wrapper exception raised by {@link ThingApi} in synchronous methods
 */
public class KnotException extends Exception {

    public KnotException() {
    }

    public KnotException(String message) {
        super(message);
    }

    public KnotException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public KnotException(Throwable throwable) {
        super(throwable);
    }
}
