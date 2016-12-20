/*
 * Copyright (c) 2016, CESAR.
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD license. See the LICENSE file for details.
 *
 */
package br.org.cesar.knot.lib.model;

/*
 Class that provides a way to configure the devices;
 */
public class ThingConfiguration {

    public long sensor_id;
    public long event_flags;
    public long time_sec;
    public long lower_limit;
    public long upper_limit;


    public ThingConfiguration() {

    }

    @Override
    public String toString() {
        return "ThingConfiguration{" +
                "sensor_id='" + sensor_id + '\'' +
                ", event_flags='" + event_flags + '\'' +
                ", time_sec='" + time_sec + '\'' +
                ", lower_limit='" + lower_limit + '\'' +
                ", upper_limit='" + upper_limit + '\'' +
                '}';

    }
}