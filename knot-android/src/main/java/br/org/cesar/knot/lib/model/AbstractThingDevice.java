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
    public List<SecurityListItem> discoverWhitelist;
    public List<SecurityListItem> configureWhitelist;
    public List<SecurityListItem> sendWhitelist;
    public List<SecurityListItem> receiveWhitelist;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    //Another device that is owner of the device
    private String owner;

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
        SecurityListItem item = new SecurityListItem();
        item.setRule("");
        item.setUuid(uuidOfDevice);
        discoverWhitelist.add(item);
    }

    /**
     * Get all devices that are in the DiscoverWhiteList
     *
     * @return List of devices that are in the DiscoverWhiteList
     */
    public List<SecurityListItem> getDiscoverWhiteList() {
        return discoverWhitelist;
    }

    /**
     * Remove a device of the WhiteList
     *
     * @param uuidOfDevice Device identification
     * @return True if the device was removed and  False if the device wasn't removed
     */
    public boolean removeDiscoverWhiteList(String uuidOfDevice) {

        boolean found = false;
        for (SecurityListItem item: discoverWhitelist) {
            if (item.getUuid().equals(uuidOfDevice)) {
                discoverWhitelist.remove(item);
                found = true;
                break;
            }
        }

        return found;
    }

    /**
     * Method used to add a new device to the configureWhitelist
     *
     * @param uuidOfDevice Device identification
     */
    public void addNewDeviceOnConfigureWhiteList(String uuidOfDevice) {
        SecurityListItem item = new SecurityListItem();
        item.setRule("");
        item.setUuid(uuidOfDevice);
        configureWhitelist.add(item);
    }

    /**
     * Get all devices that are in the ConfigureWhiteList
     *
     * @return List of devices that are in the ConfigureWhiteList
     */
    public List<SecurityListItem> getConfigureWhiteList() {
        return configureWhitelist;
    }

    /**
     * Remove a device of the WhiteList
     *
     * @param uuidOfDevice Device identification
     * @return True if the device was removed and  False if the device wasn't removed
     */
    public boolean removeConfigureWhiteList(String uuidOfDevice) {
        boolean found = false;
        for (SecurityListItem item: configureWhitelist) {
            if (item.getUuid().equals(uuidOfDevice)) {
                configureWhitelist.remove(item);
                found = true;
                break;
            }
        }

        return found;
    }


    /**
     * Method used to add a new device to the sendWhitelist
     *
     * @param uuidOfDevice Device identification
     */
    public void addNewDeviceOnSendWhiteList(String uuidOfDevice) {
        SecurityListItem item = new SecurityListItem();
        item.setRule("");
        item.setUuid(uuidOfDevice);
        sendWhitelist.add(item);
    }

    /**
     * Get all devices that are in the SendWhiteList
     *
     * @return List of devices that are in the SendWhiteList
     */
    public List<SecurityListItem> getSendWhiteList() {
        return sendWhitelist;
    }

    /**
     * Remove a device of the WhiteList
     *
     * @param uuidOfDevice Device identification
     * @return True if the device was removed and  False if the device wasn't removed
     */
    public boolean removeSendWhiteList(String uuidOfDevice) {
        boolean found = false;
        for (SecurityListItem item: sendWhitelist) {
            if (item.getUuid().equals(uuidOfDevice)) {
                sendWhitelist.remove(item);
                found = true;
                break;
            }
        }

        return found;
    }

    /**
     * Method used to add a new device to the receiveWhitelist
     *
     * @param uuidOfDevice Device identification
     */
    public void addNewDeviceOnReceiveWhitelist(String uuidOfDevice) {
        SecurityListItem item = new SecurityListItem();
        item.setRule("");
        item.setUuid(uuidOfDevice);
        receiveWhitelist.add(item);
    }

    /**
     * Get all devices that are in the ReceiveWhiteList
     *
     * @return List of devices that are in the ReceiveWhiteList
     */
    public List<SecurityListItem> getReceiveWhiteList() {
        return receiveWhitelist;
    }

    /**
     * Remove a device of the WhiteList
     *
//     * @param uuidOfDevice Device identification
     * @return True if the device was removed and  False if the device wasn't removed
     */
    public boolean removeReceiveWhiteList(String uuidOfDevice) {
        boolean found = false;
        for (SecurityListItem item: receiveWhitelist) {
            if (item.getUuid().equals(uuidOfDevice)) {
                receiveWhitelist.remove(item);
                found = true;
                break;
            }
        }

        return found;
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
