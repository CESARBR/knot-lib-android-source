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
 * Exception to throw when there are a invalid parameters
 */
public class InvalidParametersException extends Exception {

    /**
     * Instantiates a new Invalid parameter exception. Default constructor.
     *
     */
    public InvalidParametersException() {
    }

    /**
     *
     * Instantiates a new Invalid parameter exception by message.
     *
     * @param message message that is pass in exception message.
     */
    public InvalidParametersException(String message) {
        super(message);
    }

    /**
     *  Instantiates a new Invalid parameter exception by message and throwable.
     *
     * @param message message that is pass in exception message.
     * @param throwable throwable exception.
     */
    public InvalidParametersException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     *  Instantiates a new Invalid parameter exception by throwable.
     *
     * @param throwable throwable exception.
     */
    public InvalidParametersException(Throwable throwable) {
        super(throwable);
    }
}
