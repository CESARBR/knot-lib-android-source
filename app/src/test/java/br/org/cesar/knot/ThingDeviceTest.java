package br.org.cesar.knot;

import org.junit.Before;
import org.junit.Test;

import br.org.cesar.knot.lib.ThingApi;

public class ThingDeviceTest {

    private static final String END_POINT = "http://localhost:3000";

    private ThingApi mApi;

    @Before
    public void setUp() {
        mApi = ThingApi.getInstance(END_POINT);
    }

    @Test
    public void createDevice() throws Exception {

        // TODO:
    }

}