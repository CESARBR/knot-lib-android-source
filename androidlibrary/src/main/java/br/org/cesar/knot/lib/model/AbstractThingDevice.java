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

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that all other devices must extends This class has the common device elements on KNOT
 */
public abstract class AbstractThingDevice {

    public String uuid;
    public String token;
    public List<ThingConfiguration> config;

    public AbstractThingDevice() {
        config = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "AbstractThingDevice{" +
            "uuid='" + uuid + '\'' +
            ", token='" + token + '\'' +
            ", config='" + config.toString() + '\'' +
            '}';
    }
}
