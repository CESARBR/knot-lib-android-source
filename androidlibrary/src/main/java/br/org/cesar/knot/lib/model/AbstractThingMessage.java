package br.org.cesar.knot.lib.model;

/**
* Abstract class that all message must extends
* This class has the common message elements on Meshblu
*/
public class AbstractThingMessage {

    public String devices;
    public String message;
    public String timestamp;

    public AbstractThingMessage(){
    }

    @Override
    public String toString() {
        return "AbstractThingMessage{"+
                "devices='" + devices +'\'' +
                "message='" + message + '\'' +
                "timestamp='" + timestamp + '\'' +
                '}';
    }
}
