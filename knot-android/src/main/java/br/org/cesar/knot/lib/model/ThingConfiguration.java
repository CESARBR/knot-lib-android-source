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

    /**
     * Default constructor of ThingConfiguration.
     */
    public ThingConfiguration() {

    }

    /**
     * Get sensor ID value of thing configuration.
     *
     * @return sensor id value.
     */
    public long getSensorId() {
        return sensor_id;
    }

    /**
     * Set sensorId value of thing configuration.
     *
     * @param sensorId sensor id value.
     *
     * @return ThingConfiguration instance.
     */
    public ThingConfiguration setSensorId(long sensorId) {
        this.sensor_id = sensorId;
        calculateEventFlag();
        return this;
    }

    /**
     * Get time sec value of thing configuration.
     *
     * @return time sec value.
     */
    public long getTimeSec() {
        return time_sec;
    }

    /**
     * Set timeSec value of thing configuration.
     *
     * @param timeSec time sec value.
     *
     * @return ThingConfiguration instance.
     */
    public ThingConfiguration setTimeSec(long timeSec) {
        this.time_sec = timeSec;
        calculateEventFlag();

        return this;
    }

    /**
     * Get lower limit value of thing configuration.
     *
     * @return the lower limit value.
     */
    public long getLowerLimit() {
        return lower_limit;
    }

    /**
     * Set lowerLimit value of thing configuration.
     *
     * @param lowerLimit lowerLimit value.
     *
     * @return ThingConfiguration instance.
     */
    public ThingConfiguration setLowerLimit(long lowerLimit) {
        this.lower_limit = lowerLimit;
        calculateEventFlag();

        return this;
    }

    /**
     * Get upper limit value of thing configuration.
     *
     * @return the upper limit value.
     */
    public long getUpperLimit() {
        return upper_limit;
    }

    /**
     * Set upperLimit value of thing configuration.
     *
     * @param upperLimit upperLimit value.
     *
     * @return ThingConfiguration instance.
     */
    public ThingConfiguration setUpperLimit(long upperLimit) {
        this.upper_limit = upperLimit;
        calculateEventFlag();

        return this;
    }

    /**
     * Get upper limit value of thing configuration.
     *
     * @return true if user change configuration or false otherwise.
     */
    public boolean isChange() {
        return change;
    }

    /**
     * Set if has change configuration.
     *
     * @param change value if configuration change or not.
     *
     * @return ThingConfiguration instance.
     */
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