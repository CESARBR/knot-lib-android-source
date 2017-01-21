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

import br.org.cesar.knot.lib.connection.FacadeConnection;

/**
 * Wrapper exception raised by {@link FacadeConnection} in synchronous methods
 */
public class KnotException extends Exception {

    /**
     * Instantiates a new KnotException. Default constructor.
     *
     */
    public KnotException() {
    }

    /**
     *
     * Instantiates a new KnotException by message by message.
     *
     * @param message message that is pass in exception message.
     */
    public KnotException(String message) {
        super(message);
    }

    /**
     *
     * Instantiates a new KnotException by message and throwable.
     *
     * @param message message that is pass in exception message.
     * @param throwable a generic throwable exception
     */
    public KnotException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     *
     * Instantiates a new KnotException by throwable.
     *
     * @param throwable a generic throwable exception
     */
    public KnotException(Throwable throwable) {
        super(throwable);
    }
}
