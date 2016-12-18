package br.org.cesar.knot.lib.model;

import java.util.Iterator;
import java.util.List;

/**
* Abstract class that all message must extends
* This class has the common message elements on Meshblu
*/
public class AbstractThingMessage {

    private List<String> devices;
    private String message;
    private String timestamp;

    public AbstractThingMessage(){
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

    public List<String> getDevices() {
        return devices;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setDevices(List<String> devices) {
        this.devices = devices;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
