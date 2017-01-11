package br.org.cesar.knot.lib;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Objects;

/**
 * Created by usuario on 09/01/17.
 */

public class KnotSocketIo {

    private Socket mSocket;
    private String mEndPoint;


    public KnotSocketIo(String endPoint) throws URISyntaxException {

        if (endPoint != null) {
            mEndPoint = endPoint;
            mSocket = IO.socket(mEndPoint);
            mSocket.on("ready", onReady);
            mSocket.on("notReady", onNotReady);
            mSocket.connect();
        } else {
            //TODO - throws exception
        }

    }

    //https://meshblu-socketio.readme.io/docs/register
    public void createNewDevice() {

        if (mSocket.connected()) {

            JSONObject device = new JSONObject();
            try {

                JSONObject version = new JSONObject();
                version.put("version", "2.0.0");
                device.put("meshblu", version);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            mSocket.emit("register", device, new Ack() {
                @Override
                public void call(Object... args) {
                    Log.d("emidio", "teste");
                }
            });

        }

    }

    //Needs to be tested put only to remember us
    //https://meshblu-socketio.readme.io/docs/devices
    public void listDevices(JSONObject query) {
        if(mSocket.connected()) {
            mSocket.emit("register", query, new Ack() {
                @Override
                public void call(Object... args) {
                    Log.d("emidio", "teste");
                }
            });
        }
    }



    //https://meshblu-socketio.readme.io/docs/unregister
    public void deleteDevice() {
        JSONObject device = getDeviceTemp();

        if (device != null) {
            mSocket.emit("unregister", device, new Ack() {
                @Override
                public void call(Object... args) {
                    Log.d("emidio", "teste");
                }
            });
        }
    }

    //https://meshblu-socketio.readme.io/docs/identity
    public void authenticateDevice(Object device) {
        JSONObject deviceObject = getDeviceTemp();

        if (deviceObject != null) {
            mSocket.emit("identity", deviceObject);
        }
    }

    //https://meshblu-socketio.readme.io/docs/whoami
    public void whoAmI(Object device) {
        JSONObject deviObject = getDeviceTemp();

        if (deviObject != null) {
            mSocket.emit("whoami", deviObject, new Ack() {
                @Override
                public void call(Object... args) {
                    Log.d("emidio", "teste");
                }
            });
        }
    }

    //https://meshblu-socketio.readme.io/docs/update
    public void updateDevice(Object device) {

        if (mSocket.connected()) {
            JSONObject deviceObject = getDeviceTemp();
            try {
                deviceObject.put("team", "sport");
                deviceObject.put("name", "Fulano de tal");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (deviceObject != null) {

                mSocket.emit("update", deviceObject, new Ack() {
                    @Override
                    public void call(Object... args) {
                        Log.d("emidio", "teste");
                    }
                });
            }

        }

    }


    public void getDevice() {

        if (mSocket.connected()) {

        }

    }


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
