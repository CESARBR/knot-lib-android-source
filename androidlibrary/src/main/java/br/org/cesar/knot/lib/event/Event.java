/*
 * Copyright (c) 2017, CESAR.
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD license. See the LICENSE file for details.
 *
 *
 */

package br.org.cesar.knot.lib.event;

import br.org.cesar.knot.lib.exception.KnotException;

/**
 * Event interface that call when event happened.
 * @param <T> Generic type to Event.
 */
public interface Event<T> {

    /**
     * Call to show when event is finish.
     *
     * @param object Generic object
     */
    public void onEventFinish(T object);

    /**
     * Call to show when event has an error.
     *
     * @param e throw a generic exception.
     */
    public void onEventError(Exception e);
}
