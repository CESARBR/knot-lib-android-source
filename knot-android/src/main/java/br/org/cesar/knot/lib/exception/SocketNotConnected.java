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
 * Class that are used throw when there isn't connection
 *
 */
public class SocketNotConnected extends Exception {

    /**
     * Instantiates a new SocketNotConnected. Default constructor.
     *
     */
    public SocketNotConnected() {
    }

    /**
     *
     * Instantiates a new SocketNotConnected by message.
     *
     * @param message message that is pass in exception message.
     */
    public SocketNotConnected(String message) {
        super(message);
    }

    /**
     *
     * Instantiates a new SocketNotConnected by message and throwable.
     *
     * @param message message that is pass in exception message.
     * @param throwable a generic throwable exception
     */
    public SocketNotConnected(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     *
     * Instantiates a new SocketNotConnected by message and throwable.
     *
     * @param throwable a generic throwable exception
     */
    public SocketNotConnected(Throwable throwable) {
        super(throwable);
    }
}
