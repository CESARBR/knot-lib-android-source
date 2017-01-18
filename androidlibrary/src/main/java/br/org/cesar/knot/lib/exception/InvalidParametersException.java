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

public class InvalidParametersException extends Exception {

    public InvalidParametersException() {
    }

    public InvalidParametersException(String message) {
        super(message);
    }

    public InvalidParametersException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public InvalidParametersException(Throwable throwable) {
        super(throwable);
    }
}
