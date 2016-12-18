package br.org.cesar.knot.lib.model;

/**
 * Abstract class that all other data must extends
 * This class has the common data elements on Meshblu
 */
public abstract class AbstractThingData {

    public String timestamp;

    public AbstractThingData() {
    }

    @Override
    public String toString() {
        return "AbstractThingData{" +
                "timestamp='" + timestamp + '\'' +
                '}';
    }
}
