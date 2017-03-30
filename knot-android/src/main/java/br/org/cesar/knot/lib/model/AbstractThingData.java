/*
 * Copyright (c) 2017, CESAR.
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD license. See the LICENSE file for details.
 *
 *
 */

package br.org.cesar.knot.lib.model;

/**
 * Abstract class that all other data must extends
 * This class has the common data elements on Meshblu
 */
public abstract class AbstractThingData {

    public String timestamp;

    public AbstractThingData() {
    }

    @Override
    public String toString() {
        return "AbstractThingData{" +
                "timestamp='" + timestamp + '\'' +
                '}';
    }
}
