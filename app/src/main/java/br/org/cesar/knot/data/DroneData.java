package br.org.cesar.knot.data;

import br.org.cesar.knot.lib.model.AbstractThingData;

public class DroneData extends AbstractThingData {

    public long altitude;

    public DroneData() {
    }

    @Override
    public String toString() {
        return "DroneData{" +
                "altitude=" + altitude +
                "} " + super.toString();
    }
}
