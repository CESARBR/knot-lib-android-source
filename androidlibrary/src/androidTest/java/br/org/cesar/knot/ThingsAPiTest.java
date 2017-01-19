/*
 * Copyright (c) 2017, CESAR.
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD license. See the LICENSE file for details.
 *
 *
 */

package br.org.cesar.knot;

import android.support.annotation.NonNull;
import android.test.AndroidTestCase;

import junit.framework.Assert;

import br.org.cesar.knot.lib.connection.FacadeConnection;
import br.org.cesar.knot.lib.exception.InvalidDeviceOwnerStateException;
import br.org.cesar.knot.lib.exception.KnotException;
import br.org.cesar.knot.lib.model.AbstractThingDevice;


/**
 * The type Things a pi test.
 */
public class ThingsAPiTest extends AndroidTestCase {

    private static final String INVALID_URL = "http://localhost:3000";
    private static final String LOCAL_MESHBLU_WEB_SERVER = INVALID_URL;

    private static final String CUSTOM_DATA = "Custom data";
    private static final String FIRST_DEVICE = "First Device";
    private static final String SECOND_DEVICE = "Second device";


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Check if the default url was changed
        Assert.assertNotSame("You must change the default url of web server before run all unit tests", INVALID_URL, LOCAL_MESHBLU_WEB_SERVER);
        // Configure the http base url
        FacadeConnection.getInstance().setupHttp(LOCAL_MESHBLU_WEB_SERVER);

        // release the last Device owner
        checkReleaseOwnerOrFail();
    }

    /**
     * Check if the device was successfully registered on meshblu
     */
    public void testCreateDevice() {
        // create and test if the Device owner was created property
        TestImplThingDevice testImplThingDevice = new TestImplThingDevice();
        createDeviceOwnerOrFail(testImplThingDevice);
    }

    /**
     * Test claim device.
     */
    public void testClaimDevice() {
        // create the second device
        TestImplThingDevice secondDevice = new TestImplThingDevice();
        secondDevice.setCustomData(SECOND_DEVICE);

        // create the second device
        secondDevice = createDeviceOwnerOrFail(secondDevice);

        // release the device owner on facade
        checkReleaseOwnerOrFail();

        TestImplThingDevice deviceOwner = new TestImplThingDevice();
        deviceOwner.setCustomData(FIRST_DEVICE);

        // create the device owner
        createDeviceOwnerOrFail(deviceOwner);

        try {
            // assign the second device to Device Owner
            final boolean claimResult = FacadeConnection.getInstance().httpClaimDevice(secondDevice.getUuid());
            Assert.assertTrue("The device was not claim property", claimResult);
        } catch (KnotException e) {
            Assert.fail("Generic exception");
        } catch (InvalidDeviceOwnerStateException e) {
            Assert.fail("The device owner was not property created");
        }
    }

    private void checkReleaseOwnerOrFail() {
        // release the last Device owner
        FacadeConnection.getInstance().releaseDeviceOwner();

        final boolean validDeviceOwner = FacadeConnection.getInstance().isValidDeviceOwner();
        Assert.assertFalse("The device owner was not released property", validDeviceOwner);
    }

    private TestImplThingDevice createDeviceOwnerOrFail(@NonNull TestImplThingDevice testImplThingDevice) {
        // Create and register a custom device on meshblu using a sync method
        try {
            testImplThingDevice.setCustomData(CUSTOM_DATA);
            final TestImplThingDevice result = FacadeConnection.getInstance().httpCreateDevice(new TestImplThingDevice());

            // check if the given device was created on meshblu
            Assert.assertNotNull(result);

            // check if the uuid and token were genereated
            Assert.assertNotNull(result.getUuid());
            Assert.assertNotNull(result.getToken());

            // check if exists a valid DeviceOwner in cache
            final boolean validDeviceOwner = FacadeConnection.getInstance().isValidDeviceOwner();
            Assert.assertTrue("There is no DeviceOwner in cache", validDeviceOwner);

            return result;
        } catch (KnotException e) {
            Assert.fail("Device not created");
        }
        return null;
    }

    // create a loca abstractThingDevice
    private class TestImplThingDevice extends AbstractThingDevice {

        private String customData;

        /**
         * Gets custom data.
         *
         * @return the custom data
         */
        public String getCustomData() {
            return customData;
        }

        /**
         * Sets custom data.
         *
         * @param customData the custom data
         */
        public void setCustomData(String customData) {
            this.customData = customData;
        }
    }
}
