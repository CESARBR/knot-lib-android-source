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
 * The type Invalid owner state exception.
 */
public class InvalidDeviceOwnerStateException extends Exception {

    /**
     * Instantiates a new Invalid owner state exception.
     */
    public InvalidDeviceOwnerStateException() {
    }

    /**
     * Instantiates a new Invalid owner state exception.
     *
     * @param message the message
     */
    public InvalidDeviceOwnerStateException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Invalid owner state exception.
     *
     * @param message   the message
     * @param throwable the throwable
     */
    public InvalidDeviceOwnerStateException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Instantiates a new Invalid owner state exception.
     *
     * @param throwable the throwable
     */
    public InvalidDeviceOwnerStateException(Throwable throwable) {
        super(throwable);
    }
}
