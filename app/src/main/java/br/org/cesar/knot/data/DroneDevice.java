package br.org.cesar.knot.data;

import br.org.cesar.knot.lib.model.AbstractThingDevice;

public class DroneDevice extends AbstractThingDevice {

    public int color;

    public String owner;

    public DroneDevice() {
    }

    @Override
    public String toString() {
        return "DroneDevice{" +
                "uuid='" + uuid + '\'' +
                ", token='" + token + '\'' +
                ", color=" + color +
                "} " + super.toString();
    }
}
