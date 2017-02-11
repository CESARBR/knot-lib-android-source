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
public abstract class AbstractThingDevice extends AbstractDeviceOwner {

    // List of UUIDs that have some permissions
    public List<ThingConfiguration> config;
    private List<String> discoverWhitelist;
    private List<String> configureWhitelist;
    private List<String> sendWhitelist;
    private List<String> receiveWhitelist;

    //Another device that is owner of the device
    public String owner;

    /**
     * Constructor of the class
     */
    public AbstractThingDevice() {
        config = new ArrayList<>();
        discoverWhitelist = new ArrayList<>();
        configureWhitelist = new ArrayList<>();
        sendWhitelist = new ArrayList<>();
        receiveWhitelist = new ArrayList<>();
    }


    /**
     * Method used to add a new device to the discoverWhitelist
     *
     * @param uuidOfDevice Device identification
     */
    public void addNewDeviceOnDiscoverWhiteList(String uuidOfDevice) {
        discoverWhitelist.add(uuidOfDevice);
    }

    /**
     * Get all devices that are in the DiscoverWhiteList
     *
     * @return List of devices that are in the DiscoverWhiteList
     */
    public List<String> getDiscoverWhiteList() {
        return discoverWhitelist;
    }

    /**
     * Remove a device of the WhiteList
     *
     * @param uuidOfDevice Device identification
     * @return True if the device was removed and  False if the device wasn't removed
     */
    public boolean removeDiscoverWhiteList(String uuidOfDevice) {
        if (discoverWhitelist.contains(uuidOfDevice)) {
            discoverWhitelist.remove(uuidOfDevice);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method used to add a new device to the configureWhitelist
     *
     * @param uuidOfDevice Device identification
     */
    public void addNewDeviceOnConfigureWhiteList(String uuidOfDevice) {
        configureWhitelist.add(uuidOfDevice);
    }

    /**
     * Get all devices that are in the ConfigureWhiteList
     *
     * @return List of devices that are in the ConfigureWhiteList
     */
    public List<String> getConfigureWhiteList() {
        return configureWhitelist;
    }

    /**
     * Remove a device of the WhiteList
     *
     * @param uuidOfDevice Device identification
     * @return True if the device was removed and  False if the device wasn't removed
     */
    public boolean removeConfigureWhiteList(String uuidOfDevice) {
        if (configureWhitelist.contains(uuidOfDevice)) {
            configureWhitelist.remove(uuidOfDevice);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Method used to add a new device to the sendWhitelist
     *
     * @param uuidOfDevice Device identification
     */
    public void addNewDeviceOnSendWhiteList(String uuidOfDevice) {
        sendWhitelist.add(uuidOfDevice);
    }

    /**
     * Get all devices that are in the SendWhiteList
     *
     * @return List of devices that are in the SendWhiteList
     */
    public List<String> getSendWhiteList() {
        return sendWhitelist;
    }

    /**
     * Remove a device of the WhiteList
     *
     * @param uuidOfDevice Device identification
     * @return True if the device was removed and  False if the device wasn't removed
     */
    public boolean removeSendWhiteList(String uuidOfDevice) {
        if (sendWhitelist.contains(uuidOfDevice)) {
            sendWhitelist.remove(uuidOfDevice);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method used to add a new device to the receiveWhitelist
     *
     * @param uuidOfDevice Device identification
     */
    public void addNewDeviceOnReceiveWhitelist(String uuidOfDevice) {
        receiveWhitelist.add(uuidOfDevice);
    }

    /**
     * Get all devices that are in the ReceiveWhiteList
     *
     * @return List of devices that are in the ReceiveWhiteList
     */
    public List<String> getReceiveWhiteList() {
        return receiveWhitelist;
    }

    /**
     * Remove a device of the WhiteList
     *
     * @param uuidOfDevice Device identification
     * @return True if the device was removed and  False if the device wasn't removed
     */
    public boolean removeReceiveWhiteList(String uuidOfDevice) {
        if (receiveWhitelist.contains(uuidOfDevice)) {
            receiveWhitelist.remove(uuidOfDevice);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public String toString() {
        return "AbstractThingDevice{" +
                "uuid='" + getUuid() + '\'' +
                ", token='" + getToken() + '\'' +
                ", config='" + config.toString() + '\'' +
                '}';
    }
}
