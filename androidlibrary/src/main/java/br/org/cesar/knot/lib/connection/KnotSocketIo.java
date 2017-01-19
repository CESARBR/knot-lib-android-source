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

import android.app.Activity;
import android.support.annotation.NonNull;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.List;

import br.org.cesar.knot.lib.event.Event;
import br.org.cesar.knot.lib.exception.InvalidParametersException;
import br.org.cesar.knot.lib.exception.KnotException;
import br.org.cesar.knot.lib.exception.SocketNotConnected;
import br.org.cesar.knot.lib.model.AbstractThingData;
import br.org.cesar.knot.lib.model.AbstractThingDevice;
import br.org.cesar.knot.lib.model.AbstractThingMessage;
import br.org.cesar.knot.lib.model.ThingList;
import br.org.cesar.knot.lib.util.LogLib;


/**
 * The type Knot mSocket io.
 */
final class KnotSocketIo {

    /**
     * Event used to create a new device on meshblu
     */
    private static String EVENT_CREATE_DEVICE = "device";

    /**
     * Event used to delete a device on meshblu
     */
    private static String EVENT_UNREGISTER_DEVICE = "unregister";

    /**
     * Event used to register a socket on meshblu
     */
    private static String EVENT_IDENTITY_DEVICE = "identity";

    /**
     * Event used to get information about the device
     */
    private static String EVENT_WHOAMI = "whoami";

    /**
     * Event used to update information of the device
     */
    private static String EVENT_UPDATE_DEVICE = "update";

    /**
     * Event used to insert a new data on server
     */
    private static String EVENT_INSERT_DATA = "data";

    /**
     * Event used to get all datas of specific device
     */
    private static String EVENT_GET_DATA = "getdata";

    /**
     * Event used to get all devices of specific device
     */
    private static String EVENT_GET_DEVICES = "devices";

    /**
     * Event used to get a specific device
     */
    private static String EVENT_GET_DEVICE = "device";

    /**
     * Event used to claim a device
     */
    private static String EVENT_CLAIM_DEVICE = "claimdevice";

    /**
     * Event used to register method to receive connect information
     */
    private static String EVENT_READY = "ready";

    /**
     * Event used to register method to receive connect information
     */
    private static String EVENT_NOT_READY = "notReady";

    /**
     * Event used to register method to receive messages
     */
    private static String EVENT_MESSAGE = "message";

    /**
     * Event used to register method to receive device information
     */
    private static String EVENT_CONFIG = "config";

    /**
     * First position of result of an event emitted
     */
    private static int FIRST_EVENT_RECEIVED = 0;

    /**
     * Tag that identify an error of server
     */
    private static String ERROR = "error";

    /**
     * Tag that identify success of server
     */
    private static String FROMUUID = "fromUuid";

    /**
     * Tag that identify uuid of the device
     */
    private static String UUID = "uuid";

    /**
     * Tag that identify the token of the device
     */
    private static String TOKEN = "token";

    /**
     * Tag that represents the data of the device
     */
    private static String DATA = "data";

    /**
     * Tag that represents the devices of the json result
     */
    private static String DEVICES = "devices";

    /**
     * Tag that represents the devices of the json result
     */
    private static String DEVICE = "device";

    /**
     * Tag used to make the device parser
     */
    private static String FROM = "from";

    /**
     * The socket that make the communication between client and server
     */
    private Socket mSocket;

    /**
     * Empty json
     */
    private static final String EMPTY_JSON = "[]";

    /**
     * Flag that show if the socket was registered
     */
    private boolean mDeviceRegistered;

    /**
     * Manipulate json objects
     */
    private Gson mGson;

    /**
     * Callback used to transmit all messages to client
     */
    private  Event<AbstractThingMessage> mOnMessageEventCallback;

    /**
     *   Type of class used to build a new message received
     */
    private AbstractThingMessage mMessageClass;

    /**
     *   Type of class used to build a device that was modified
     */
    private AbstractThingDevice  mConfigClass;

    /**
     * Callback used to transmit all modifications to client
     */
    private  Event<AbstractThingDevice> mOnConfigEventCallback;

    /**
     * This event is called when the server response if the device was registered
     */
    private Emitter.Listener onReady = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            mDeviceRegistered = true;
        }
    };

    /**
     * This event is called when the server response if the device wasn't registered
     */
    private Emitter.Listener onNotReady = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            mDeviceRegistered = false;
        }
    };

    /**
     * This event is called when the device receive a neu message
     */
    private Emitter.Listener onMessageReceived = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            if(args!=null && args.length>0 && args[FIRST_EVENT_RECEIVED]!=null){
                parserToMessage(args[FIRST_EVENT_RECEIVED].toString());
            }

        }
    };

    /**
     * This event is called when the device is updated
     */
    private Emitter.Listener onConfigDevice = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            if(args!=null && args.length>0 && args[FIRST_EVENT_RECEIVED]!=null){
                parserToConfig(args[FIRST_EVENT_RECEIVED].toString());
            }
        }
    };

    /**
     * Instantiates a new Knot mSocket io.
     *
     * @param endPoint the end point
     * @throws SocketNotConnected the mSocket not connected
     */
    public KnotSocketIo(@NonNull String endPoint) throws SocketNotConnected {
        this();
        connect(endPoint);
    }

    /**
     * You must call the {@link #connect(String)} to open a valid mSocket
     */
    public KnotSocketIo() {
        this.mGson = new Gson();
    }

    /**
     * Open a new mSocket with the meshblus
     *
     * @param endPoint endpoint of gateway
     * @throws SocketNotConnected if its not possible to open a mSocket
     */
    public void connect(@NonNull String endPoint) throws SocketNotConnected {
        try {
            mSocket = IO.socket(endPoint);

            mSocket.on(EVENT_READY, onReady);
            mSocket.on(EVENT_NOT_READY, onNotReady);
            mSocket.on(EVENT_MESSAGE,onMessageReceived);
            mSocket.on(EVENT_CONFIG,onConfigDevice);

            mSocket.connect();
        } catch (URISyntaxException e) {
            LogLib.printE("Its not possible to connect on mSocket", e);
        }
    }

    /**
     * Disconnect and invalidate the current mSocket. You must call {@link #connect(String)} to open a valid mSocket
     * before to do any action on meshblu
     */
    public void disconnect() {
        if (isSocketConnected()) {
            mSocket.disconnect();
            mSocket = null;
        }
    }


    /**
     * Generate a new Device in Meshblu instance
     *
     * @param device         model sample to create a new one. Basically this device model
     *                       contains attributes that will be saved into Meshblu.
     *                       Please note that uuid and token will always
     *                       be generated by Meshblu (please see AbstractThingDevice).
     *                       It is important set the custom attribute for your classes
     * @param callbackResult Callback for this method
     * @return New device with meshblu token and uuid values
     * @throws KnotException
     * @throws SocketNotConnected
     * @see AbstractThingDevice
     * <p>
     * Check the reference on @see <a href="https://meshblu-socketio.readme.io/docs/register</a>
     */
    public <T extends AbstractThingDevice> void createNewDevice(final T device, final Event<T> callbackResult) throws SocketNotConnected {
        if (isSocketConnected() && device != null) {
            mSocket.emit(EVENT_CREATE_DEVICE, device, new Ack() {
                @Override
                public void call(Object... args) {
                    // check if it's necessary to return the result of event
                    if (callbackResult != null) {
                        Object firstPosition;
                        if ((firstPosition = args[FIRST_EVENT_RECEIVED]) != null) {
                            final String json = (String) firstPosition;
                            final T result = (T) mGson.fromJson(json, device.getClass());

                            //call the callback
                            callbackResult.onEventFinish(result);
                        } else {
                            callbackResult.onEventError(new KnotException());
                        }
                    }
                }
            });
        } else {
            throw new SocketNotConnected("Socket not ready or connected");
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
     * @throws SocketNotConnected
     * <p>
     * Check the reference on @see <a https://meshblu-socketio.readme.io/docs/unregister</a>
     */
    public <T extends AbstractThingDevice> void deleteDevice(final T device, final Event<T> callbackResult) throws SocketNotConnected {

        if (isSocketRegistered() && isSocketConnected() && device != null) {
            JSONObject deviceToDelete = getNecessaryDeviceInformation(device);

            if (deviceToDelete != null) {
                mSocket.emit(EVENT_UNREGISTER_DEVICE, deviceToDelete, new Ack() {
                    @Override
                    public void call(Object... args) {
                        //Get First element of the array
                        if (args.length > 0 && args[FIRST_EVENT_RECEIVED] != null) {
                            JsonElement jsonElement = new JsonParser().parse(args[FIRST_EVENT_RECEIVED].toString());
                            JsonObject jsonObject = jsonElement.getAsJsonObject();
                            if (jsonObject.get(ERROR) != null) {
                                callbackResult.onEventError(new KnotException(jsonObject.get(ERROR).toString()));
                            } else if (jsonObject.get(FROMUUID) != null) {
                                callbackResult.onEventFinish(device);
                            } else {
                                callbackResult.onEventError(new KnotException("Unknown error"));
                            }

                        } else {
                            callbackResult.onEventError(new KnotException("Failed to delete file"));
                        }

                    }
                });
            }
        } else {
            throw new SocketNotConnected("Socket not ready or connected");
        }
    }


    /**
     * The API Needs to call this method to authenticate a device with the socket communication.
     *
     * @param device         the device
     * @param callbackResult Callback for this method
     * @throws KnotException
     * @throws SocketNotConnected
     * @throws InvalidParametersException Check the reference on
     * <p>
     * @see <a https://meshblu-socketio.readme.io/docs/identity</a>
     */
    public <T extends AbstractThingDevice> void authenticateDevice(final T device, final Event<T> callbackResult) throws SocketNotConnected, InvalidParametersException {

        if (isSocketConnected()) {
            if (device != null && callbackResult != null) {
                String json = mGson.toJson(device);

                JSONObject deviceToAutheticate = null;
                try {
                    deviceToAutheticate = new JSONObject(json);
                } catch (JSONException e) {
                    callbackResult.onEventError(new KnotException(e.getMessage()));
                }

                mSocket.emit(EVENT_IDENTITY_DEVICE, deviceToAutheticate);
            } else {
                throw new InvalidParametersException("Invalid parameters");
            }

        } else {
            throw new SocketNotConnected("Socket not ready or connected");
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
     *  <p>
     * @see <a https://meshblu-socketio.readme.io/docs/whoami </a>
     */
    //
    public <T extends AbstractThingDevice> void whoAmI(final T device, final Event<T> callbackResult) throws JSONException, SocketNotConnected, InvalidParametersException {

        if (isSocketConnected() && isSocketRegistered()) {
            if (device != null && callbackResult != null) {
                JSONObject whoAmi = getNecessaryDeviceInformation(device);

                if (whoAmi != null) {
                    mSocket.emit(EVENT_WHOAMI, whoAmi, new Ack() {
                        @Override
                        public void call(Object... args) {
                            try {
                                T result = (T) mGson.fromJson(args[FIRST_EVENT_RECEIVED].toString(), device.getClass());
                                callbackResult.onEventFinish(result);
                            } catch (Exception e) {
                                callbackResult.onEventError(new KnotException());
                            }
                        }
                    });
                }
            } else {
                throw new InvalidParametersException("Invalid parameters");
            }
        } else {
            throw new SocketNotConnected("Socket not ready or connected");
        }
    }

    /**
     * Update an existent device
     *
     * @param device         the identifier of device (uuid)
     * @param callbackResult Callback for this method
     * @throws KnotException
     * @throws SocketNotConnected
     * @throws InvalidParametersException
     *  <p>
     * Check the reference on @see <a https://meshblu-socketio.readme.io/docs/update</a>
     */
    public <T extends AbstractThingDevice> void updateDevice(final T device, final Event<T> callbackResult) throws SocketNotConnected, InvalidParametersException {

        if (isSocketConnected() && isSocketRegistered()) {
            if (device != null && callbackResult != null) {
                String json = mGson.toJson(device);

                JSONObject deviceToUpdate = null;
                try {
                    deviceToUpdate = new JSONObject(json);
                } catch (JSONException e) {
                    throw new InvalidParametersException("Invalid parameters. Please, check your device object");
                }

                if (deviceToUpdate != null) {

                    mSocket.emit(EVENT_UPDATE_DEVICE, deviceToUpdate, new Ack() {
                        @Override
                        public void call(Object... args) {
                            //Get First element of the array
                            if (args.length > 0 && args[FIRST_EVENT_RECEIVED] != null) {
                                JsonElement jsonElement = new JsonParser().parse(args[FIRST_EVENT_RECEIVED].toString());
                                JsonObject jsonObject = jsonElement.getAsJsonObject();
                                if (jsonObject.get(ERROR) != null) {
                                    callbackResult.onEventError(new KnotException(jsonObject.get(ERROR).toString()));
                                } else {
                                    T result = (T) mGson.fromJson(args[FIRST_EVENT_RECEIVED].toString(), device.getClass());
                                    callbackResult.onEventFinish(result);
                                }

                            } else {
                                callbackResult.onEventError(new KnotException("Failed to update file"));
                            }
                        }
                    });
                }
            } else {
                throw new InvalidParametersException("Invalid parameters");
            }
        } else {
            throw new SocketNotConnected("Socket not ready or connected");
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
    public <T extends AbstractThingDevice> void claimDevice(final T device, final Event<T> callbackResult) throws SocketNotConnected, InvalidParametersException, JSONException {

        if (isSocketConnected() && isSocketRegistered()) {
            if (device != null && callbackResult != null) {
                JSONObject uuidDevice = new JSONObject();
                uuidDevice.put(UUID, device);

                mSocket.emit(EVENT_CLAIM_DEVICE, uuidDevice, new Ack() {
                    @Override
                    public void call(Object... args) {
                        if (args != null && args.length > 0) {
                            JsonElement jsonElement = new JsonParser().parse(args[FIRST_EVENT_RECEIVED].toString());
                            JsonObject jsonObject = jsonElement.getAsJsonObject();
                            if (jsonObject.get(ERROR) != null && !jsonObject.get(ERROR).isJsonNull()) {
                                callbackResult.onEventError(new KnotException(jsonObject.get(ERROR).toString()));
                            } else {
                                callbackResult.onEventFinish(true);
                            }
                        } else {
                            callbackResult.onEventError(new KnotException("Error in reading the result"));
                        }
                    }
                });
            } else {
                throw new InvalidParametersException("Invalid parameters");
            }
        } else {
            throw new SocketNotConnected("Socket not ready or connected");
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
    public <T extends AbstractThingDevice> void getDevice(final T typeClass, String uuid, final Event<T> callbackResult) throws JSONException, InvalidParametersException, SocketNotConnected {

        if (isSocketConnected() && isSocketRegistered()) {
            if (typeClass != null && callbackResult != null && uuid != null) {

                JSONObject uuidDevice = new JSONObject();
                uuidDevice.put(UUID, uuid);

                mSocket.emit(EVENT_GET_DEVICE, uuidDevice, new Ack() {
                    @Override
                    public void call(Object... args) {

                        if (args != null && args.length > 0) {

                            JsonElement jsonElement = new JsonParser().parse(args[FIRST_EVENT_RECEIVED].toString());
                            JsonObject jsonObject = jsonElement.getAsJsonObject();
                            if (jsonObject.get(ERROR) != null && !jsonObject.get(ERROR).isJsonNull()) {
                                callbackResult.onEventError(new KnotException(jsonObject.get(ERROR).toString()));
                            } else if (jsonObject.get(FROM) != null) {
                                JsonObject json = jsonObject.get(DEVICE).getAsJsonObject();
                                try {
                                    T result = (T) mGson.fromJson(json.toString(), typeClass.getClass());
                                    callbackResult.onEventFinish(result);
                                } catch (Exception e) {
                                    callbackResult.onEventError(new KnotException("Error in reading the result"));
                                }
                            } else {
                                callbackResult.onEventError(new KnotException("Unknown error"));
                            }
                        } else {
                            callbackResult.onEventError(new KnotException("Error in reading the result"));
                        }
                    }
                });

            } else {
                throw new InvalidParametersException("Invalid parameters");
            }
        } else {
            throw new SocketNotConnected("Socket not ready or connected");
        }

    }

    /**
     * This method return a list devices of the specific owner
     *
     * @param typeThing      Generic type of list.
     * @param query          Query to find devices
     * @param callbackResult List of devices
     * @throws KnotException
     *  <p>
     * @see <ahttps://meshblu-socketio.readme.io/docs/devices </a>
     */
    public <T extends AbstractThingDevice> void getDeviceList(final ThingList<T> typeThing, JSONObject
            query, final Event<T> callbackResult) throws KnotException, SocketNotConnected, InvalidParametersException {
        if (isSocketConnected() && isSocketRegistered()) {
            if (typeThing != null && query != null && callbackResult != null) {

                mSocket.emit(EVENT_GET_DEVICES, query, new Ack() {
                    @Override
                    public void call(Object... args) {
                        List<T> result = null;
                        try {
                            JsonElement jsonElement = new JsonParser().parse(args[FIRST_EVENT_RECEIVED].toString());
                            JsonArray jsonArray = jsonElement.getAsJsonObject().getAsJsonArray(DEVICES);
                            if (jsonArray != null || jsonArray.size() > 0) {
                                result = mGson.fromJson(jsonArray, typeThing);
                            } else {
                                result = mGson.fromJson(EMPTY_JSON, typeThing);
                            }
                            callbackResult.onEventFinish((T) result);
                        } catch (Exception e) {
                            callbackResult.onEventError();
                        }
                    }
                });
            } else {
                throw new InvalidParametersException("Invalid parameters");
            }
        } else {
            throw new SocketNotConnected("Socket not ready or connected");
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
    public <T extends AbstractThingData> void createData(final String uuid, final T data, final Event<T> callbackResult) throws JSONException, InvalidParametersException, SocketNotConnected {

        if (isSocketConnected() && isSocketRegistered()) {
            if (data != null && callbackResult != null) {
                String json = mGson.toJson(data);
                JSONObject dataToSend = new JSONObject(json);
                dataToSend.put(UUID, uuid);

                mSocket.emit(EVENT_INSERT_DATA, dataToSend, new Ack() {
                    @Override
                    public void call(Object... args) {
                        //When the result is success, the response return empty.
                        if (args != null && args.length == 0) {
                            callbackResult.onEventFinish(data);
                        } else {
                            callbackResult.onEventError();
                        }
                    }
                });
            } else {
                throw new InvalidParametersException("Invalid parameters");
            }
        } else {
            throw new SocketNotConnected("Socket not ready or connected");
        }
    }

    /**
     * Get all data of the specific device
     *
     * @param type           List of abstracts objects
     * @param uuid           UUid of device
     * @param callbackResult Callback for this method
     * @throws InvalidParametersException
     * @throws SocketNotConnected
     */
    public <T extends AbstractThingData> void getData(final ThingList<T> type, String uuid, final Event<List<T>> callbackResult) throws InvalidParametersException, SocketNotConnected {

        if (isSocketConnected() && isSocketRegistered()) {
            if (uuid != null && callbackResult != null) {

                JSONObject dataToSend = new JSONObject();
                try {
                    dataToSend.put(UUID, uuid);
                } catch (JSONException e) {
                    callbackResult.onEventError(new KnotException());
                }

                mSocket.emit(EVENT_GET_DATA, dataToSend, new Ack() {
                    @Override
                    public void call(Object... args) {
                        if (args != null && args.length > 0) {
                            List<T> result = null;
                            JsonElement jsonElement = new JsonParser().parse(args[FIRST_EVENT_RECEIVED].toString());
                            JsonArray jsonArray = jsonElement.getAsJsonObject().getAsJsonArray(DATA);

                            if (jsonArray != null || jsonArray.size() > 0) {
                                result = mGson.fromJson(jsonArray.toString(), type);
                            } else {
                                result = mGson.fromJson(EMPTY_JSON, type);
                            }

                            callbackResult.onEventFinish(result);
                        } else {
                            callbackResult.onEventError(new KnotException());
                        }
                    }
                });

            } else {
                throw new InvalidParametersException("Invalid parameters");
            }

        } else {
            throw new SocketNotConnected("Socket not ready or connected");
        }

    }

    /**
     * This method sends a message to a device list of your choice.
     *
     * @param message AbstractThingMessage that will be sent
     * @param <T> Type of Object
     * @throws InvalidParametersException
     * @throws SocketNotConnected
     * @throws JSONException
     * @see <a> https://meshblu-socketio.readme.io/docs/message </a>
     */
    public <T extends AbstractThingMessage> void sendMessage(final T message) throws InvalidParametersException, SocketNotConnected, JSONException {

        if (isSocketConnected() && isSocketRegistered()) {
            if (message != null) {
                String json = mGson.toJson(message);
                JSONObject dataToSend = new JSONObject(json);

                mSocket.emit(EVENT_MESSAGE, dataToSend);

            } else {
                throw new InvalidParametersException("Invalid parameters");
            }
        } else {
            throw new SocketNotConnected("Socket not ready or connected");
        }


    }

    /**
     * Set callback to receive message to your device
     * @param messageEventCallback Callback to receive message
     */
    public <T extends AbstractThingMessage> void setCallbackToMessageEvent(final Event<AbstractThingMessage> messageEventCallback, T classOfT){
        mOnMessageEventCallback = messageEventCallback;
        mMessageClass = classOfT;
    }

    /**
     * Set callback to capture information of the your device
     * @param configEventeCallback Callback to receive device information
     */
    public <T extends AbstractThingDevice>  void setCallbackToConfigEvent(final Event<AbstractThingDevice> configEventeCallback, T classOfT){
        mOnConfigEventCallback = configEventeCallback;
        mConfigClass = classOfT;
    }

    /**
     *  Method used to make a parser from json to AbstractThingMessage.
     *
     * @param json Json that has message information
     * @param <T> type of classe
     */
    private  <T extends AbstractThingMessage> void parserToMessage(String json){
        if(json!=null && mOnMessageEventCallback!=null && mMessageClass !=null){
            T result = (T) mGson.fromJson(json, mMessageClass.getClass());

            if(result!=null){
                mOnMessageEventCallback.onEventFinish(result);
            }
        }
    }

    /**
     *  Method used to make a parser from json to AbstractThingDevice.
     *
     * @param json Json that has device's information
     * @param <T> type of classe
     */
    private  <T extends AbstractThingDevice> void parserToConfig(String json){

        if(json!=null){
            T result = (T) mGson.fromJson(json, mConfigClass.getClass());

            if(result!=null){
                mOnConfigEventCallback.onEventFinish(result);
            }
        }
    }

    /**
     * Building a JsonObject to use in some methods
     *
     * @param device that contains all information
     * @return JsonObject
     */
    private <T extends AbstractThingDevice> JSONObject getNecessaryDeviceInformation(T device) {
        JSONObject deviceInformation = new JSONObject();

        try {
            deviceInformation.put(UUID, device.uuid);
            deviceInformation.put(TOKEN, device.token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return deviceInformation;
    }

    /**
     * Is mSocket connected boolean.
     *
     * @return the boolean
     */
    public boolean isSocketConnected() {
        return mSocket != null && mSocket.connected();
    }

    /**
     * Is mSocket registered on meshblu .
     *
     * @return the boolean
     */
    public boolean isSocketRegistered() {
        return isSocketConnected() && mDeviceRegistered;
    }

}
