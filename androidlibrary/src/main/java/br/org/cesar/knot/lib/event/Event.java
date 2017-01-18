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

public interface Event<T> {

    public void onEventFinish(T object);

    public void onEventFinish(boolean result);

    public void onEventError();

    public void onEventError(KnotException e);

    public void onEventError(Exception e);
}
