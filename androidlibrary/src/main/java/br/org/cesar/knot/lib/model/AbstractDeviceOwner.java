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

import android.support.annotation.NonNull;

/**
 * The type Abstract owner.
 */
public class AbstractDeviceOwner {

    // Device Identification
    private String uuid;

    // Device Authentication
    private String token;

    /**
     * Instantiates a new Abstract owner.
     */
    public AbstractDeviceOwner(){}

    /**
     * Instantiates a new Abstract owner.
     *
     * @param uuid  the uuid
     * @param token the token
     */
    public AbstractDeviceOwner(@NonNull String uuid, @NonNull String token) {
        this.uuid = uuid;
        this.token = token;
    }

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets uuid.
     *
     * @param uuid the uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
