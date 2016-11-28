package br.org.cesar.knot.data;

import com.google.gson.annotations.SerializedName;

import br.org.cesar.knot.lib.model.AbstractThingMessage;

/**
 * Created by wallace on 27/11/16.
 */

public class BroadcastMessage extends AbstractThingMessage {

    @SerializedName("test")
    private String test;

    public BroadcastMessage() {
    }

    @Override
    public String toString() {
        return "BroadcastMessage{" +
                "test=" + test +
                "} " + super.toString();
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}