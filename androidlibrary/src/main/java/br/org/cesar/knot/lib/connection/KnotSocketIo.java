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
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import br.org.cesar.knot.lib.event.Event;
import br.org.cesar.knot.lib.exception.SocketNotConnected;
import br.org.cesar.knot.lib.model.AbstractThingDevice;
import br.org.cesar.knot.lib.util.LogLib;


/**
 * The type Knot socket io.
 */
final class KnotSocketIo {

    /**
     * Event used to create a new device on meshblu
     */
    private static String EVENT_CREATE_DEVICE = "device";

    /**
     * First position of result of an event emitted
     */
    private static int FIRST_EVENT_RECEIVED = 0;

    private Socket socket;
    private Gson gson;
    private Emitter.Listener onReady = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            Log.d("emidio", "teste");
            updateDevice(null);
        }
    };
    private Emitter.Listener onNotReady = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            Log.d("emidio", "teste");
        }
    };

    /**
     * Instantiates a new Knot socket io.
     *
     * @param endPoint the end point
     * @throws SocketNotConnected the socket not connected
     */
    public KnotSocketIo(@NonNull String endPoint) throws SocketNotConnected {
        this();
        connect(endPoint);
    }

    /**
     * You must call the {@link #connect(String)} to open a valid socket
     */
    public KnotSocketIo() {
        this.gson = new Gson();
    }

    /**
     * Open a new socket with the meshblus
     *
     * @param endPoint endpoint of gateway
     * @throws SocketNotConnected if its not possible to open a socket
     */
    public void connect(@NonNull String endPoint) throws SocketNotConnected {
        try {
            socket = IO.socket(endPoint);
            socket.connect();
        } catch (URISyntaxException e) {
            LogLib.printE("Its not possible to connect on socket", e);
        }
    }

    /**
     * Disconnect and invalidate the current socket. You must call {@link #connect(String)} to open a valid socket
     * before to do any action on meshblu
     */
    public void disconnect() {
        if (isSocketConnected()) {
            socket.disconnect();
            socket = null;
        }
    }

    /**
     * Is socket connected boolean.
     *
     * @return the boolean
     */
    public boolean isSocketConnected() {
        return socket != null && socket.connected();
    }

    /**
     * Create new device on meshblu. Check the reference on @see <a href="https://meshblu-socketio.readme.io/docs/register</a>
     */
    public <T extends AbstractThingDevice> void createNewDevice(final T device, final Event<T> callbackResult) throws SocketNotConnected {
        if (isSocketConnected()) {
            socket.emit(EVENT_CREATE_DEVICE, device, new Ack() {
                @Override
                public void call(Object... args) {
                    // check if it's necessary to return the result of event
                    if (callbackResult != null) {
                        Object firstPostition;
                        if ((firstPostition = args[FIRST_EVENT_RECEIVED]) != null) {
                            final String json = (String) firstPostition;
                            final T result = (T) gson.fromJson(json, device.getClass());

                            //call the callback
                            callbackResult.onEventFinish(result);
                        } else {
                            callbackResult.onEventError();
                        }
                    }
                }
            });
        } else {
            throw new SocketNotConnected("Socket not ready or connected");
        }
    }

    /**
     * List devices.
     *
     * @param query the query
     */
//Needs to be tested put only to remember us
    //https://meshblu-socketio.readme.io/docs/devices
    public void listDevices(JSONObject query) {
        if (socket.connected()) {
            socket.emit("register", query, new Ack() {
                @Override
                public void call(Object... args) {
                    Log.d("emidio", "teste");
                }
            });
        }
    }

    /**
     * Delete device.
     */
//https://meshblu-socketio.readme.io/docs/unregister
    public void deleteDevice() {
        JSONObject device = getDeviceTemp();

        if (device != null) {
            socket.emit("unregister", device, new Ack() {
                @Override
                public void call(Object... args) {
                    Log.d("emidio", "teste");
                }
            });
        }
    }

    /**
     * Authenticate device.
     *
     * @param device the device
     */
//https://meshblu-socketio.readme.io/docs/identity
    public void authenticateDevice(Object device) {
        JSONObject deviceObject = getDeviceTemp();

        if (deviceObject != null) {
            socket.emit("identity", deviceObject);
        }
    }

    /**
     * Who am i.
     *
     * @param device the device
     */
//https://meshblu-socketio.readme.io/docs/whoami
    public void whoAmI(Object device) {
        JSONObject deviObject = getDeviceTemp();

        if (deviObject != null) {
            socket.emit("whoami", deviObject, new Ack() {
                @Override
                public void call(Object... args) {
                    Log.d("emidio", "teste");
                }
            });
        }
    }

    /**
     * Update device.
     *
     * @param device the device
     */
//https://meshblu-socketio.readme.io/docs/update
    public void updateDevice(Object device) {

        if (socket.connected()) {
            JSONObject deviceObject = getDeviceTemp();
            try {
                deviceObject.put("team", "sport");
                deviceObject.put("name", "Fulano de tal");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (deviceObject != null) {

                socket.emit("update", deviceObject, new Ack() {
                    @Override
                    public void call(Object... args) {
                        Log.d("emidio", "teste");
                    }
                });
            }

        }

    }

    /**
     * Gets device.
     */
    public void getDevice() {

        if (socket.connected()) {

        }

    }

    /**
     * Gets device temp.
     *
     * @return the device temp
     */
//temp
    public JSONObject getDeviceTemp() {
        JSONObject deviceObject = new JSONObject();
        try {
            deviceObject.put("uuid", "f32ac874-55fe-4bda-8a0f-588dbe9b0000");
            deviceObject.put("token", "2b76efa91e81709ef01c42534f19c92656b1900d");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return deviceObject;
    }


}
