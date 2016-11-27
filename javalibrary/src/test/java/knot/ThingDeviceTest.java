package knot;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.cesar.knot.ThingApi;
import br.org.cesar.knot.ThingDevice;
import knot.data.DroneDevice;

public class ThingDeviceTest {

    private static final String END_POINT = "https://meshblu:3000";
    private static final String DEVICE_UUID = "";
    private static final String DEVICE_TOKEN = "";
    private static final int COLOR = 191819;

    @Before
    public void setUp() {
        ThingApi.initialize(END_POINT);
    }

    @Test
    public void testSave() throws Exception {

        ThingDevice simpleDevice = new ThingDevice();
        simpleDevice.save();

        Assert.assertNotNull(simpleDevice.getId());
        Assert.assertNotNull(simpleDevice.getToken());

        DroneDevice droneDevice = new DroneDevice();
        droneDevice.setOwner(simpleDevice);
        droneDevice.setColor(COLOR);
        droneDevice.save();

        Assert.assertNotNull(droneDevice.getId());
        Assert.assertNotNull(droneDevice.getToken());
    }

    @Test
    public void existentDeviceOnServerShouldReturnValidThingDevice() {
        DroneDevice droneDevice = new DroneDevice(DEVICE_UUID, DEVICE_TOKEN);
        droneDevice.fetch();

        Assert.assertEquals(COLOR, droneDevice.getColor());
    }
}