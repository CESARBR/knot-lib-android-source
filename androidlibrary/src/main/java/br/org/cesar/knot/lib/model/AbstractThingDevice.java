/*
 * Copyright (c) 2016, CESAR.
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD license. See the LICENSE file for details.
 *
 */
package br.org.cesar.knot.lib.model;

/**
 * Abstract class that all other devices must extends
 * This class has the common device elements on Meshblu
 */
public abstract class AbstractThingDevice {

    public String uuid;
    public String token;
    public ThingConfiguration abstractThingConfiguration;

    public AbstractThingDevice() {
    }

    @Override
    public String toString() {
        return "AbstractThingDevice{" +
                "uuid='" + uuid + '\'' +
                ", token='" + token + '\'' +
                ", abstractThingConfiguration='" + abstractThingConfiguration.toString() + '\'' +
                '}';
    }
}
