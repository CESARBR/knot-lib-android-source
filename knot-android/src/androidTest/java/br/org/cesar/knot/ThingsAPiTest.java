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

import java.util.ArrayList;
import java.util.List;

import br.org.cesar.knot.lib.connection.FacadeConnection;
import br.org.cesar.knot.lib.exception.InvalidDeviceOwnerStateException;
import br.org.cesar.knot.lib.exception.KnotException;
import br.org.cesar.knot.lib.model.AbstractThingData;
import br.org.cesar.knot.lib.model.AbstractThingDevice;
import br.org.cesar.knot.lib.model.AbstractThingMessage;
import br.org.cesar.knot.lib.model.KnotList;
import br.org.cesar.knot.lib.model.KnotQueryData;


/**
 * The type Things a pi test.
 */
public class ThingsAPiTest extends AndroidTestCase {

    private static final String INVALID_URL = "http://localhost:3000";
    private static final String LOCAL_MESHBLU_WEB_SERVER = "http://your.IP:3000"; // Put the address of your server, for example: http://172.26.67.70:3000

    private static final String CUSTOM_DATA = "Custom data";
    private static final String FIRST_DEVICE = "First Device";
    private static final String SECOND_DEVICE = "Second device";

    private static final String UUID_OWNER = "OWNER_UUID"; // Put the uuid of the Owner
    private static final String TOKEN_OWNER = "TOKEN_UUID"; // Put the token of the Owner

    private static final double TEMPERATURE_TEST = 10;

    private static final String TEST_SEND_MESSAGE = "Sending a test message";


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Check if the default url was changed
        Assert.assertNotSame("You must change the default url of web server before run all unit tests", INVALID_URL, LOCAL_MESHBLU_WEB_SERVER);
        // Configure the http base url
        FacadeConnection.getInstance().setupHttp(LOCAL_MESHBLU_WEB_SERVER, UUID_OWNER,TOKEN_OWNER);

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

        try {
            // assign the second device to Device Owner
            final boolean claimResult = FacadeConnection.getInstance().httpClaimDevice(secondDevice.getUuid());
            Assert.assertTrue("The device was not claim property", claimResult);
        } catch (KnotException e) {
            Assert.fail(e.getMessage());
            Assert.fail("Generic exception");
        } catch (InvalidDeviceOwnerStateException e) {
            Assert.fail(e.getMessage());
            Assert.fail("The device owner was not property created");
        }
    }

    private TestImplThingDevice createDeviceOwnerOrFail(@NonNull TestImplThingDevice testImplThingDevice) {
        // Create and register a custom device on meshblu using a sync method
        try {
            testImplThingDevice.setCustomData(CUSTOM_DATA);
            final TestImplThingDevice result = FacadeConnection.getInstance().httpCreateDevice(testImplThingDevice);

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
            Assert.fail(e.getMessage());
            Assert.fail("Device not created");
        }
        return null;
    }


    /**
     * Test WhoAmI method;
     */
    public void testWhoAmI(){
        //create a TestImplThingDevice device to test
        TestImplThingDevice device = new TestImplThingDevice();

        try {

            device =  FacadeConnection.getInstance().httpWhoAmI(device.getClass());

            Assert.assertNotNull(device.getUuid());

            Assert.assertEquals("The call of WhoAMi is correct", device.getUuid(),UUID_OWNER);

        } catch (KnotException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        } catch (InvalidDeviceOwnerStateException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Test updateDevice method
     */
    public void testUpdateDevice(){
        // create the device to test
        TestImplThingDevice device = new TestImplThingDevice();
        device.setCustomData(CUSTOM_DATA);

        // create the second device
        device = createDeviceOwnerOrFail(device);

        Assert.assertEquals("The same CustomData", device.getCustomData(), CUSTOM_DATA);

        //Change the custom data
        device.setCustomData(FIRST_DEVICE);
        try {
            device =  FacadeConnection.getInstance().httpUpdateDevice(device.getUuid(), device);

            Assert.assertNotNull(device);

            Assert.assertNotNull(device.getCustomData());

            Assert.assertEquals("The custom data needs to be FirstDevice", device.getCustomData(), FIRST_DEVICE);
        } catch (KnotException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        } catch (InvalidDeviceOwnerStateException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Test DeleteDevice Method
     */
    public void testDeleteDevice(){
        //Create Device To test
        TestImplThingDevice device = getDevice();

        Assert.assertNotNull(device);

        try {
            Boolean result = FacadeConnection.getInstance().httpDeleteDevice(device.getUuid());

            Assert.assertTrue("The device was deleted", result);

        } catch (KnotException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        } catch (InvalidDeviceOwnerStateException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Test GetDeviceList method
     */
    public void testGetDevicesList(){

        KnotList<TestImplThingDevice> list = new KnotList<>(TestImplThingDevice.class);

        try {
            List<TestImplThingDevice> listOfDevice  =  FacadeConnection.getInstance().httpGetDeviceList(list);

            Assert.assertNotNull(listOfDevice);

        } catch (KnotException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        } catch (InvalidDeviceOwnerStateException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test CreateData method
     */
    public void testCreateData(){
        TestImplThingData data = new TestImplThingData();
        data.setTemperature(TEMPERATURE_TEST);

        try {
            boolean result  =  FacadeConnection.getInstance().httpCreateData(UUID_OWNER, data);

            Assert.assertTrue("The data was created ", result);
        } catch (KnotException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        } catch (InvalidDeviceOwnerStateException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test GetDataList method
     */
    public void testGetDataList(){

        KnotList<TestImplThingData>  listOfData = new KnotList<>(TestImplThingData.class);
        KnotQueryData knotQueryData = new KnotQueryData();

        try {
            List<TestImplThingData> listResulted = FacadeConnection.getInstance().httpGetDataList(UUID_OWNER, knotQueryData,listOfData);

            Assert.assertNotNull(listResulted);

        } catch (KnotException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        } catch (InvalidDeviceOwnerStateException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Test sendMessage method
     */
    public void testSendMessage() {
        //create message object that will be sent
        TestImplThingMessage message = new TestImplThingMessage();

        // create the second device
        TestImplThingDevice device = new TestImplThingDevice();

        // create the second device
        device = createDeviceOwnerOrFail(device);

        Assert.assertNotNull(device.getUuid());

        message.getDevices().add(device.getUuid());

        message.setMessage(TEST_SEND_MESSAGE);

        try {
            TestImplThingMessage messageSent =  FacadeConnection.getInstance().httpSendMessage(message);

            Assert.assertNotNull(messageSent);
        } catch (KnotException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        } catch (InvalidDeviceOwnerStateException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }


    }

    /**
     * Used to test getDevice method
     *
     * @return a device
     */
    private TestImplThingDevice getDevice(){
        // create the second device
        TestImplThingDevice device = new TestImplThingDevice();
        device.setCustomData(CUSTOM_DATA);

        // create the second device
        device = createDeviceOwnerOrFail(device);

        try {
            TestImplThingDevice sameDevice =  FacadeConnection.getInstance().httpGetDevice(device.getUuid(),device.getClass());
            Assert.assertNotNull(sameDevice);

            Assert.assertEquals("UUid need to be equals", sameDevice.getUuid(), device.getUuid());


            return sameDevice;
        } catch (KnotException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        } catch (InvalidDeviceOwnerStateException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }

        return null;

    }


    // create a local abstractThingDevice
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

    // create a local AbstractThingData
    private class TestImplThingData extends AbstractThingData {
        /**
         * Temperature of device
         */
        private double mTemperature;

        /**
         * Get temperature of device
         *
         * @return the temperature data
         */
        public double getTemperature(){
            return mTemperature;
        }

        /**
         * Sets temperature of device
         *
         * @param temperature the temperature value
         */
        public void setTemperature(double temperature){
            mTemperature = temperature;
        }

    }

    // create a local AbstractThingMessage
    private class TestImplThingMessage extends AbstractThingMessage {

    }
}
