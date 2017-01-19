/*
 * Copyright (c) 2017, CESAR.
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD license. See the LICENSE file for details.
 *
 *
 */

package br.org.cesar.knot.lib.exception;

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
