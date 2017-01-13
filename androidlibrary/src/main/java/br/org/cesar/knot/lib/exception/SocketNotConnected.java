/*
 *
 *  Copyright (c) 2017, CESAR.
 *  All rights reserved.
 *
 *  This software may be modified and distributed under the terms
 *  of the BSD license. See the LICENSE file for details.
 *
 */

package br.org.cesar.knot.lib.exception;

public class SocketNotConnected extends Exception {

    public SocketNotConnected() {
    }

    public SocketNotConnected(String message) {
        super(message);
    }

    public SocketNotConnected(String message, Throwable throwable) {
        super(message, throwable);
    }

    public SocketNotConnected(Throwable throwable) {
        super(throwable);
    }
}
