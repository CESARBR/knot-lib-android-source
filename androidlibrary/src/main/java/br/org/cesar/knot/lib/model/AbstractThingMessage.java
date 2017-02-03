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
import java.util.Iterator;
import java.util.List;

/**
* Abstract class that all message must extends
* This class has the common message elements on KNOT
*/
public abstract class AbstractThingMessage {

    private List<String> devices;
    private String message;
    private String timestamp;

    public AbstractThingMessage(){
        devices = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "AbstractThingMessage{"+
                "devices='" + deviceListInterator(devices.iterator()) +'\'' +
                "message='" + message + '\'' +
                "timestamp='" + timestamp + '\'' +
                '}';
    }

    private String deviceListInterator(Iterator<String> devicesUUID) {
        String devices = "";
        while(devicesUUID.hasNext()) {
            String element = devicesUUID.next();
            devices += element + ", ";
        }
        return devices;
    }

    /**
     *  Get list of device that will received or sent message.
     *
     * @return list of devices in meshblu cloud
     */
    public List<String> getDevices() {
        return devices;
    }

    /**
     * Get message value that will received or sent message.
     *
     * @return message value.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get time stamp of message will received or sent.
     *
     * @return time stamp that message occurred
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Set devices list that will received the message.
     *
     * @param devices devices list that will received message.
     */
    public void setDevices(List<String> devices) {
        this.devices = devices;
    }

    /**
     * Set message that will send or received.
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Set time stamp that message occurred
     *
     * @param timestamp time stamp that message occurred
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
