package br.org.cesar.knot.data;

import com.google.gson.annotations.SerializedName;

import br.org.cesar.knot.lib.model.AbstractThingMessage;

/**
 * Created by wallace on 27/11/16.
 */

public class BroadcastMessage extends AbstractThingMessage {

    @SerializedName("test")
    public String test;

    public BroadcastMessage(String test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "BroadcastMessage{" +
                "test=" + message +
                "} " + super.toString();
    }
}