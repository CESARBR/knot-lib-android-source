package br.org.cesar.knot.lib.model;

/**
 * Abstract class that all other devices must extends
 * This class has the common device elements on Meshblu
 */
public abstract class AbstractThingDevice {

    public String uuid;
    public String token;

    public AbstractThingDevice() {
    }

    @Override
    public String toString() {
        return "AbstractThingDevice{" +
                "uuid='" + uuid + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
