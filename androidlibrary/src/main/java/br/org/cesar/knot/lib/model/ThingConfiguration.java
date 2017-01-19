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
 * Class that provides a way to configure the devices;
 */
public class ThingConfiguration {

    private static final int KNOT_EVT_FLAG_NONE = 0;
    private static final int KNOT_EVT_FLAG_TIME = 1;
    private static final int KNOT_EVT_FLAG_LOWER_THRESHOLD = 2;
    private static final int KNOT_EVT_FLAG_UPPER_THRESHOLD = 4;
    private static final int KNOT_EVT_FLAG_CHANGE = 8;

    private long sensor_id;
    private long event_flags;
    private long time_sec;
    private Long lower_limit;
    private Long upper_limit;
    private transient boolean change;

    public ThingConfiguration() {

    }

    public long getSensorId() {
        return sensor_id;
    }

    public ThingConfiguration setSensorId(long sensorId) {
        this.sensor_id = sensorId;
        calculateEventFlag();
        return this;
    }

    public long getTimeSec() {
        return time_sec;
    }

    public ThingConfiguration setTimeSec(long timeSec) {
        this.time_sec = timeSec;
        calculateEventFlag();

        return this;
    }

    public long getLowerLimit() {
        return lower_limit;
    }

    public ThingConfiguration setLowerLimit(long lowerLimit) {
        this.lower_limit = lowerLimit;
        calculateEventFlag();

        return this;
    }

    public long getUpperLimit() {
        return upper_limit;
    }

    public ThingConfiguration setUpperLimit(long upperLimit) {
        this.upper_limit = upperLimit;
        calculateEventFlag();

        return this;
    }

    public boolean isChange() {
        return change;
    }

    public ThingConfiguration setChange(boolean change) {
        this.change = change;
        calculateEventFlag();

        return this;
    }

    private void calculateEventFlag() {
        event_flags = KNOT_EVT_FLAG_NONE;

        if (lower_limit != null) {
            event_flags += KNOT_EVT_FLAG_LOWER_THRESHOLD;
        }

        if (upper_limit != null) {
            event_flags += KNOT_EVT_FLAG_UPPER_THRESHOLD;
        }

        if (time_sec > KNOT_EVT_FLAG_NONE) {
            event_flags += KNOT_EVT_FLAG_TIME;
        }

        if (change) {
            event_flags += KNOT_EVT_FLAG_CHANGE;
        }
    }

    @Override
    public String toString() {
        return "config{" +
            "sensor_id='" + sensor_id + '\'' +
            ", event_flags='" + event_flags + '\'' +
            ", time_sec='" + time_sec + '\'' +
            ", lower_limit='" + lower_limit + '\'' +
            ", upper_limit='" + upper_limit + '\'' +
            '}';

    }
}