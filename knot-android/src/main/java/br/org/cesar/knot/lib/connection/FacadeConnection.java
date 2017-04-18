/*
 * Copyright (c) 2017, CESAR.
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD license. See the LICENSE file for details.
 *
 *
 */

package br.org.cesar.knot.lib.connection;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.org.cesar.knot.lib.event.Event;
import br.org.cesar.knot.lib.exception.InvalidDeviceOwnerStateException;
import br.org.cesar.knot.lib.exception.InvalidParametersException;
import br.org.cesar.knot.lib.exception.KnotException;
import br.org.cesar.knot.lib.exception.SocketNotConnected;
import br.org.cesar.knot.lib.model.AbstractThingData;
import br.org.cesar.knot.lib.model.AbstractThingDevice;
import br.org.cesar.knot.lib.model.AbstractThingMessage;
import br.org.cesar.knot.lib.model.KnotQueryData;
import br.org.cesar.knot.lib.model.KnotList;

/**
 * The type Facade connection.
 */
public class FacadeConnection {


    private static final Object lock = new Object();
    private static FacadeConnection sInstance;

    private KnotSocketIo mSocketIO;
    private ThingApi mThingApi;

    private FacadeConnection() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static FacadeConnection getInstance() {
        synchronized (lock) {
            if (sInstance == null) {
                sInstance = new FacadeConnection();
            }
            return sInstance;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // SocketIO
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Sets socket io.
     */
    public synchronized void createSocketIo(){
        if(mSocketIO == null){
            mSocketIO = new KnotSocketIo();
        }
    }

    /**
     * Sets socket io.
     *
     * @param uuidOwner The device identification
     * @param  tokenOwner the
     * @throws SocketNotConnected the socket not connected
     */
    public synchronized void setupSocketIO(@NonNull String uuidOwner, @NonNull String tokenOwner) {
        if (mSocketIO != null) {
            mSocketIO.setupKnotSocketIo(uuidOwner, tokenOwner);
        }
    }

    /**
     * Socket io create new device on meshblu
     *
     * @param <T>            the type parameter
     * @param device         the device
     * @param callbackResult the callback result
     * @throws SocketNotConnected the socket not connected
     */
    public synchronized <T extends AbstractThingDevice> void socketIOCreateNewDevice(final T device, final Event<T> callbackResult) throws SocketNotConnected, JSONException {
        if (mSocketIO != null && isSocketConnected()) {
            mSocketIO.createNewDevice(device, callbackResult);
        } else {
            throw new SocketNotConnected("Socket not connected or invalid. Did you call the method setupSocketIO?");
        }
    }

    /**
     * Turns the device belongs to someone. When a device is created in
     * Meshblu, it is an orphan device. In other words, everyone can made any
     * changes on this device. After claim a device, only the
     * owner can delete or update it.
     * Note: In Meshblu, the owner for one device IS another device.
     *
     * @param device         the identifier of device (uuid)
     * @param callbackResult Callback for this method
     * @throws KnotException
     * @throws SocketNotConnected <p>
     *                            Check the reference on @see <a https://meshblu-socketio.readme.io/docs/unregister</a>
     */
    public <T extends AbstractThingDevice> void socketIODeleteDevice(final T device, final Event<T> callbackResult) throws SocketNotConnected {
        if (mSocketIO != null && isSocketConnected()) {
            mSocketIO.deleteDevice(device, callbackResult);
        } else {
            throw new SocketNotConnected("Socket not connected or invalid. Did you call the method setupSocketIO?");
        }
    }

    /**
     * The API Needs to call this method to authenticate a device with the socket communication.
     *
     * @param callbackResult Callback for this method
     * @throws KnotException
     * @throws SocketNotConnected
     * @throws InvalidParametersException Check the reference on
     *                                    <p>
     * @see <a https://meshblu-socketio.readme.io/docs/identity</a>
     */
    public <T extends AbstractThingDevice> void socketIOAuthenticateDevice(final Event<Boolean> callbackResult) throws SocketNotConnected, InvalidParametersException {
        if (mSocketIO != null && isSocketConnected()) {
            mSocketIO.authenticateDevice(callbackResult);
        } else {
            throw new SocketNotConnected("Socket not connected or invalid. Did you call the method setupSocketIO?");
        }
    }

    /**
     * Get all information regarding the device.
     *
     * @param callbackResult Callback for this method
     * @param device         the device
     * @throws KnotException
     * @throws SocketNotConnected
     * @throws InvalidParametersException Check the reference on
     *                                    <p>
     * @see <a https://meshblu-socketio.readme.io/docs/whoami </a>
     */
    //
    public <T extends AbstractThingDevice> void socketIOWhoAmI(final T device, final Event<T> callbackResult) throws JSONException, SocketNotConnected, InvalidParametersException {
        if (mSocketIO != null && isSocketConnected()) {
            mSocketIO.whoAmI(device, callbackResult);
        } else {
            throw new SocketNotConnected("Socket not connected or invalid. Did you call the method setupSocketIO?");
        }

    }

    /**
     * Update an existent device
     *
     * @param device         the identifier of device (uuid)
     * @param callbackResult Callback for this method
     * @throws KnotException
     * @throws SocketNotConnected
     * @throws InvalidParametersException <p>
     *                                    Check the reference on @see <a https://meshblu-socketio.readme.io/docs/update</a>
     */
    public <T extends AbstractThingDevice> void socketIOUpdateDevice(final T device, final Event<T> callbackResult) throws SocketNotConnected, InvalidParametersException {
        if (mSocketIO != null && isSocketConnected()) {
            mSocketIO.updateDevice(device, callbackResult);
        } else {
            throw new SocketNotConnected("Socket not connected or invalid. Did you call the method setupSocketIO?");
        }
    }

    /**
     * Method used to claim a specif device;
     *
     * @param device         device Wanted
     * @param callbackResult Callback for this method
     * @throws SocketNotConnected
     * @throws InvalidParametersException
     * @throws JSONException
     */
    public <T extends AbstractThingDevice> void socketIOClaimDevice(final T device, final Event<Boolean> callbackResult) throws SocketNotConnected, InvalidParametersException, JSONException {
        if (mSocketIO != null && isSocketConnected()) {
            mSocketIO.claimDevice(device, callbackResult);
        } else {
            throw new SocketNotConnected("Socket not connected or invalid. Did you call the method setupSocketIO?");
        }
    }

    /**
     * This method returns a instance of the device
     *
     * @param typeClass      The specific genericized type of src.
     * @param uuid           The device identification to find a device on server
     * @param callbackResult Callback for this method
     * @param <T>            the type of the desired object
     * @throws JSONException
     */
    public <T extends AbstractThingDevice> void socketIOGetDevice(final T typeClass, String uuid, final Event<T> callbackResult) throws JSONException, InvalidParametersException, SocketNotConnected {
        if (mSocketIO != null && isSocketConnected()) {
            mSocketIO.getDevice(typeClass, uuid, callbackResult);
        } else {
            throw new SocketNotConnected("Socket not connected or invalid. Did you call the method setupSocketIO?");
        }
    }

    /**
     * This method return a list devices of the specific owner
     *
     * @param typeThing      Generic type of list.
     * @param query          Query to find devices
     * @param callbackResult List of devices
     * @throws KnotException <p>
     * @see <ahttps://meshblu-socketio.readme.io/docs/devices </a>
     */
    public <T extends AbstractThingDevice> void socketIOGetDeviceList(final KnotList<T> typeThing, JSONObject
            query, final Event<List<T>> callbackResult) throws KnotException, SocketNotConnected, InvalidParametersException {
        if (mSocketIO != null && isSocketConnected()) {
            mSocketIO.getDeviceList(typeThing, query, callbackResult);
        } else {
            throw new SocketNotConnected("Socket not connected or invalid. Did you call the method setupSocketIO?");
        }
    }

    /**
     * Create data for one device. If the device has an owner, it is necessary that the owner
     * param be the same of the device owner.
     *
     * @param data           data that will be created for device
     * @param callbackResult Callback for this method
     * @throws KnotException
     * @throws SocketNotConnected
     * @throws InvalidParametersException
     */
    public <T extends AbstractThingData> void socketIOCreateData(final String uuid, final T data, final Event<T> callbackResult) throws JSONException, InvalidParametersException, SocketNotConnected {
        if (mSocketIO != null && isSocketConnected()) {
            mSocketIO.createData(uuid, data, callbackResult);
        } else {
            throw new SocketNotConnected("Socket not connected or invalid. Did you call the method setupSocketIO?");
        }
    }

    /**
     * Get all data of the specific device
     *
     * @param type           List of abstracts objects
     * @param uuid           UUid of device
     * @param deviceToken    token of the device
     * @param knotQueryData  Date query
     * @param callbackResult Callback for this method
     * @throws InvalidParametersException
     * @throws SocketNotConnected
     */
    public <T extends AbstractThingData> void socketIOGetData(final KnotList<T> type, String uuid, String deviceToken, KnotQueryData knotQueryData, final Event<List<T>> callbackResult) throws InvalidParametersException, SocketNotConnected {
        if (mSocketIO != null && isSocketConnected()) {
            mSocketIO.getData(type, uuid,deviceToken,knotQueryData, callbackResult);
        } else {
            throw new SocketNotConnected("Socket not connected or invalid. Did you call the method setupSocketIO?");
        }
    }

    /**
     * This method sends a message to a device list of your choice.
     *
     * @param message AbstractThingMessage that will be sent
     * @param <T>     Type of Object
     * @throws InvalidParametersException
     * @throws SocketNotConnected
     * @throws JSONException
     * @see <a> https://meshblu-socketio.readme.io/docs/message </a>
     */
    public <T extends AbstractThingMessage> void socketIOSendMessage(final T message) throws InvalidParametersException, SocketNotConnected, JSONException {
        if (mSocketIO != null && isSocketConnected()) {
            mSocketIO.sendMessage(message);
        } else {
            throw new SocketNotConnected("Socket not connected or invalid. Did you call the method setupSocketIO?");
        }
    }

    /**
     * Set callback to receive message to your device
     *
     * @param messageEventCallback Callback to receive message
     */
    public <T extends AbstractThingMessage> void socketIOSetCallbackToMessageEvent(final Event<AbstractThingMessage> messageEventCallback, T classOfT) throws SocketNotConnected {
        if (mSocketIO != null && isSocketConnected()) {
            mSocketIO.setCallbackToMessageEvent(messageEventCallback, classOfT);
        } else {
            throw new SocketNotConnected("Socket not connected or invalid. Did you call the method setupSocketIO?");
        }
    }

    /**
     * Set callback to capture information of the your device
     *
     * @param configEventeCallback Callback to receive device information
     */
    public <T extends AbstractThingDevice> void socketIOSetCallbackToConfigEvent(final Event<AbstractThingDevice> configEventeCallback, T classOfT) throws SocketNotConnected {
        if (mSocketIO != null && isSocketConnected()) {
            mSocketIO.setCallbackToConfigEvent(configEventeCallback, classOfT);
        } else {
            throw new SocketNotConnected("Socket not connected or invalid. Did you call the method setupSocketIO?");
        }
    }

    /**
     * Is socket connected boolean.
     *
     * @return the boolean
     */
    public synchronized boolean isSocketConnected() {
        if (mSocketIO != null) {
            return mSocketIO.isSocketConnected();
        } else {
            return false;
        }
    }


    /**
     * Open a new socket with the meshblus
     *
     * @param endPoint endpoint of gateway
     * @throws SocketNotConnected if its not possible to open a socket
     */
    public void connectSocket(@NonNull String endPoint, Event<Boolean> callbackResult) throws SocketNotConnected {
        disconnectSocket();
        if (mSocketIO != null) {
            mSocketIO.connect(endPoint,callbackResult);
        }
    }

    /**
     * Disconnect and invalidate the current socket. You must call {@link #connectSocket(String,Event)} to open a valid socket
     * before to do any action on meshblu
     */
    public void disconnectSocket() {
        if (isSocketConnected()) {
            mSocketIO.disconnect();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Http
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Setup the http api
     *
     * @param endPoint the end point
     */
    public void setupHttp(@NonNull String endPoint,@NonNull String ownerUuid,@NonNull String ownerToken) {
        mThingApi = new ThingApi(endPoint, ownerUuid, ownerToken);
    }

    /**
     * release the reference of local AbstractDeviceOwner
     *
     * @throws IllegalStateException
     */
    public void releaseDeviceOwner() throws IllegalStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            mThingApi.releaseDeviceOwner();
        }
    }

    /**
     * Check if exists a valid Device Owner in cache
     *
     * @return true if exists a valid DeviceOwner
     * @throws IllegalStateException
     */
    public boolean isValidDeviceOwner() throws IllegalStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            return mThingApi.isValidDeviceOwner();
        }
    }

    /**
     * Generate a new Device in Meshblu instance
     *
     * @param device model sample to create a new one. Basically this device model
     *               contains attributes that will be saved into Meshblu.
     *               Please note that uuid and token will always
     *               be generated by Meshblu (please see AbstractThingDevice).
     *               It is important set the custom attribute for your classes
     * @return New device with meshblu token and uuid values
     * @throws KnotException
     * @see AbstractThingDevice
     */
    public <T extends AbstractThingDevice> T httpCreateDevice(T device) throws IllegalStateException, KnotException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            return mThingApi.createDevice(device);
        }
    }

    /**
     * Async version of {@link #httpCreateDevice(AbstractThingDevice)}
     *
     * @param device   model sample to create a new one. Basically this device model
     *                 contains attributes that will be saved into Meshblu.
     *                 Please note that uuid and token will always
     *                 be generated by Meshblu (please see AbstractThingDevice).
     *                 It is important set the custom attribute for your classes
     * @param callback Callback for this method
     * @see AbstractThingDevice
     */
    public <T extends AbstractThingDevice> void httpCreateDevice(final T device, final Event<T> callback) throws IllegalStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            mThingApi.createDevice(device, callback);
        }
    }

    /**
     * Turns the device belongs to someone. When a device is created in
     * Meshblu, it is an orphan device. In other words, everyone can made any
     * changes on this device. After claim a device, only the
     * owner can delete or update it.
     * Note: In Meshblu, the owner for one device IS another device.
     *
     * @param device the identifier of device (uuid)
     * @return a boolean value to indicate if the device could be claimed
     * @throws KnotException
     */
    public Boolean httpClaimDevice(String device) throws KnotException, IllegalStateException, InvalidDeviceOwnerStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            return mThingApi.claimDevice(device);
        }
    }

    /**
     * Async version of {@link #httpClaimDevice(String)}
     *
     * @param device   the identifier of device (uuid)
     * @param callback Callback for this method
     */
    public void httpClaimDevice(final String device, final Event<Boolean> callback) throws IllegalStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            mThingApi.claimDevice(device, callback);
        }
    }

    /**
     * Update an existent device
     *
     * @param device the identifier of device (uuid)
     * @return the object updated
     * @throws KnotException
     */
    public <T extends AbstractThingDevice> T httpUpdateDevice(String id, T device) throws KnotException, IllegalStateException, InvalidDeviceOwnerStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            return mThingApi.updateDevice(id, device);
        }
    }

    /**
     * Async version of {@link #httpUpdateDevice(String, AbstractThingDevice)}
     *
     * @param device   the identifier of device (uuid)
     * @param callback Callback for this method
     */
    public <T extends AbstractThingDevice> void httpUpdateDevice(final String id, final T device, final Event<T> callback) throws IllegalStateException {

        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            mThingApi.updateDevice(id, device, callback);
        }
    }

    /**
     * Delete a device from Meshblu instance. If the device is an orphan one,
     * anyone can delete it. However if the device has an owner,
     * only it can execute this action.
     *
     * @param device the device identifier (uuid)
     * @return a boolean to indicate if the device was deleted
     * @throws KnotException
     */
    public boolean httpDeleteDevice(String device) throws KnotException, IllegalStateException, InvalidDeviceOwnerStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            return mThingApi.deleteDevice(device);
        }
    }

    /**
     * Async version of {@link ThingApi#deleteDevice(String)}
     *
     * @param device   the device identifier (uuid)
     * @param callback Callback for this method
     */
    public void httpDeleteDevice(final String device, final Event<Boolean> callback) throws IllegalStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            mThingApi.deleteDevice(device, callback);
        }
    }

    /**
     * Get all information regarding the device.
     *
     * @param clazz The class for this device. Meshblu works with any type of objects and
     *              it is necessary deserialize the return to a valid object.
     *              Note: The class parameter should be a extension of {@link AbstractThingDevice}
     * @return an json element containing device informations
     * @throws KnotException
     */
    public <T extends AbstractThingDevice> T httpWhoAmI(Class<T> clazz) throws KnotException, IllegalStateException, InvalidDeviceOwnerStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            return mThingApi.whoAmI(clazz);
        }
    }

    /**
     * Async version of {@link #httpWhoAmI(Class)}
     *
     * @param clazz    The class for this device. Meshblu works with any type of objects and
     *                 it is necessary deserialize the return to a valid object.
     *                 Note: The class parameter should be a extension of {@link AbstractThingDevice}
     * @param callback Callback for this method
     * @return an object based on the class parameter
     */

    public <T extends AbstractThingDevice> void httpWhoAmI(final Class<T> clazz, final Event<T> callback) throws IllegalStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            mThingApi.whoAmI(clazz, callback);
        }
    }

    /**
     * Get a specific device from Meshblu instance.
     *
     * @param device the device identifier (uuid)
     * @param clazz  The class for this device. Meshblu works with any type of objects and
     *               it is necessary deserialize the return to a valid object.
     *               Note: The class parameter should be a extension of {@link AbstractThingDevice}
     * @return an object based on the class parameter
     * @throws KnotException
     */
    public <T extends AbstractThingDevice> T httpGetDevice(String device, Class<T> clazz) throws KnotException, IllegalStateException, InvalidDeviceOwnerStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            return mThingApi.getDevice(device, clazz);
        }
    }

    /**
     * Async version of {@link #httpGetDevice(String, Class)}
     *
     * @param device   the device identifier (uuid)
     * @param clazz    The class for this device. Meshblu works with any type of objects and
     *                 it is necessary deserialize the return to a valid object.
     *                 Note: The class parameter should be a extension of {@link AbstractThingDevice}
     * @param callback Callback for this method
     * @return an object based on the class parameter
     */
    public <T extends AbstractThingDevice> void httpGetDevice(final String device, final Class<T> clazz, final Event<T> callback) throws IllegalStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            mThingApi.getDevice(device, clazz, callback);
        }
    }

    /**
     * Get a specific device's gateway from Meshblu instance.
     *
     * @param device the device identifier (uuid)
     * @param clazz  The class for this device. Meshblu works with any type of objects and
     *               it is necessary deserialize the return to a valid object.
     *               Note: The class parameter should be a extension of {@link AbstractThingDevice}
     * @return an object based on the class parameter
     * @throws KnotException
     */
    public <T extends AbstractThingDevice> T httpGetDeviceGateway(String device, Class<T> clazz) throws KnotException, IllegalStateException, InvalidDeviceOwnerStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            return mThingApi.getDeviceGateway(device, clazz);
        }
    }

    /**
     * Async version of {@link #httpGetDeviceGateway(String, Class)}
     *
     * @param device   the device identifier (uuid)
     * @param clazz    The class for this device. Meshblu works with any type of objects and
     *                 it is necessary deserialize the return to a valid object.
     *                 Note: The class parameter should be a extension of {@link AbstractThingDevice}
     * @param callback Callback for this method
     * @return an object based on the class parameter
     */
    public <T extends AbstractThingDevice> void httpGetDeviceGateway(final String device, final Class<T> clazz, final Event<T> callback) throws IllegalStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            mThingApi.getDeviceGateway(device, clazz, callback);
        }
    }

    /**
     * Get all devices those are claimed by one owner
     *
     * @param type object that will define what elements will returned by this method
     * @return a List with all devices those belongs to the owner
     * @throws KnotException
     */
    public <T extends AbstractThingDevice> List<T> httpGetDeviceList(final KnotList<T> type) throws KnotException, IllegalStateException, InvalidDeviceOwnerStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            return mThingApi.getDeviceList(type);
        }
    }

    /**
     * Async version of {@link #httpGetDeviceList(KnotList<T>)}
     *
     * @param type     object that will define what elements will returned by this method
     * @param callback
     * @return a List with all devices those belongs to the owner
     * @throws KnotException KnotException
     */
    public <T extends AbstractThingDevice> void httpGetDeviceList(final KnotList<T> type, final Event<List<T>> callback) throws IllegalStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            mThingApi.getDeviceList(type, callback);
        }
    }

    /**
     * Create data for one device. If the device has an owner, it is necessary that the owner
     * param be the same of the device owner.
     *
     * @param device the device identifier (uuid)
     * @param data   data that will be created for device
     * @return a boolean value to indicate if the data could be create for device
     * @throws KnotException
     */
    public <T extends AbstractThingData> boolean httpCreateData(String device, T data) throws KnotException, IllegalStateException, InvalidDeviceOwnerStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            return mThingApi.createData(device, data);
        }
    }

    /**
     * Async version of {@link #httpCreateData(String, AbstractThingData)}
     *
     * @param device   the device identifier (uuid)
     * @param data     data that will be created for device
     * @param callback Callback for this method
     * @return a boolean value to indicate if the data could be create for device
     * @throws KnotException
     */
    public <T extends AbstractThingData> void httpCreateData(final String device, final T data, final Event<Boolean> callback) throws IllegalStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            mThingApi.createData(device, data, callback);
        }
    }

    /**
     * @param device the device identifier (uuid)
     * @param type   object that will define what elements will returned by this method
     * @return a List with data of the device
     * @throws KnotException
     */
    public <T extends AbstractThingData> List<T> httpGetDataList(String device, KnotQueryData knotQueryData,final KnotList<T> type) throws KnotException, IllegalStateException, InvalidDeviceOwnerStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            return mThingApi.getDataList(device, type,knotQueryData);
        }
    }

    /**
     * Async version of {@link #httpGetDataList(String,KnotQueryData ,KnotList<T>)}
     *
     * @param device   the device identifier (uuid)
     * @param type     object that will define what elements will returned by this method
     * @param callback Callback for this method
     * @return a List with data of the device
     */
    public <T extends AbstractThingData> void httpGetDataList(final String device,KnotQueryData knotQueryData ,final KnotList<T> type, final Event<List<T>> callback) throws IllegalStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            mThingApi.getDataList(device, type, knotQueryData,callback);
        }
    }

    /**
     * Send a message in Meshblu instance
     *
     * @param message model sample to create a new message. Basically this message model
     *                contains attributes that will be send into Meshblu.
     * @return New message with meshblu content.
     * @throws KnotException
     * @see AbstractThingMessage
     */
    public <T extends AbstractThingMessage> T httpSendMessage(T message) throws KnotException, IllegalStateException, InvalidDeviceOwnerStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            return mThingApi.sendMessage(message);
        }
    }

    /**
     * Send a message in Meshblu instance
     *
     * @param message model sample to create a new message. Basically this message model
     *                contains attributes that will be send into Meshblu.
     * @return New message with meshblu content.
     * @see AbstractThingMessage
     */
    public <T extends AbstractThingMessage> void httpSendMessage(final T message, final Event<T> callback) throws IllegalStateException {
        if (mThingApi == null) {
            throw new IllegalStateException("Did you call the method setupHttp?");
        } else {
            mThingApi.sendMessage(message, callback);
        }
    }


}