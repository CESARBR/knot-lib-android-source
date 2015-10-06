package com.octoblu.meshblukit;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.nkzawa.emitter.Emitter;
import com.octoblu.sanejsonobject.SaneJSONObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Meshblu extends Emitter {
    private final String TAG = "Meshblu";
    public static final String REGISTER = "register";
    public static final String WHOAMI = "whoami";
    public static final String MESSAGE = "message";
    public static final String CLAIM_DEVICE = "claimdevice";
    public static final String UPDATE_DEVICE = "update";
    public static final String SEND_DATA = "send_data";
    public static final String DELETE_DEVICE = "delete_device";
    public static final String GET_DEVICE = "get_device";
    public static final String GET_DATA = "get_data";
    public static final String RESET_TOKEN = "reset_token";
    public static final String GET_PUBLIC_KEY = "get_public_key";
    public static final String GENERATED_TOKEN = "generated_token";
    public static final String ERROR = "error";

    private String MESHBLU_URL = "https://meshblu.octoblu.com";
    private final Context context;
    public String uuid, token;


    public Meshblu(SaneJSONObject meshbluConfig, Context context){
        setCredentials(meshbluConfig);
        this.context = context;
    }

    public void setCredentials(SaneJSONObject meshbluConfig){
        this.uuid = meshbluConfig.getStringOrNull("uuid");
        this.token = meshbluConfig.getStringOrNull("token");
        Integer port = meshbluConfig.getInteger("port", 80);
        String portString = "";
        String protocol = "http";
        switch(port){
            case 443:
                protocol = "https";
                break;
            case 80:
                break;
            default:
                portString = String.format(":%i", port);
                break;
        }
        String server = meshbluConfig.getStringOrNull("server");
        if(server == null){
            server = "meshblu.octoblu.com";
        }
        this.MESHBLU_URL = String.format("%s://%s%s", protocol, server, portString);
    }

    public Boolean isRegistered(){
        return this.uuid != null;
    }

    public void register(SaneJSONObject properties) {
        Log.d(TAG, "Registering device");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = String.format("%s/devices", MESHBLU_URL);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, properties, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                emit(REGISTER, jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                emit(ERROR, volleyError);
            }
        });

        queue.add(request);
    }

    public void generateToken(String uuid) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = String.format("%s/devices/%s/tokens", MESHBLU_URL, uuid);

        SaneJSONObject data = new SaneJSONObject();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                emit(GENERATED_TOKEN, jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                emit(ERROR, volleyError);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Meshblu.this.getHeaders();
            }
        };;

        queue.add(request);
    }

    public void whoami() {
        Log.d(TAG, "Getting gateblu device");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = String.format("%s/v2/whoami", MESHBLU_URL);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) { emit(WHOAMI, jsonObject); }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error getting device", volleyError);
                emit(ERROR, volleyError);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Meshblu.this.getHeaders();
            }
        };

        queue.add(request);
    }

    public void message(SaneJSONObject message) {
        Log.d(TAG, "Sending Message");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = String.format("%s/messages", MESHBLU_URL);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, message, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) { emit(MESSAGE); }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error sending message", volleyError);
                emit(ERROR, volleyError);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Meshblu.this.getHeaders();
            }
        };

        queue.add(request);
    }

    public void updateDevice(String uuid, SaneJSONObject properties) {
        Log.d(TAG, "Updating device");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = String.format("%s/v2/devices/%s", MESHBLU_URL, uuid);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PATCH, url, properties, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) { emit(UPDATE_DEVICE); }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error updating device", volleyError);
                emit(ERROR, volleyError);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Meshblu.this.getHeaders();
            }
        };

        queue.add(request);
    }

    public void claimDevice(String uuid) {
        Log.d(TAG, "Updating device");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = String.format("%s/claimdevice/%s", MESHBLU_URL, uuid);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) { emit(CLAIM_DEVICE); }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error updating device", volleyError);
                emit(ERROR, volleyError);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Meshblu.this.getHeaders();
            }
        };

        queue.add(request);
    }
    public void sendData(String uuid, SaneJSONObject message) {
        Log.d(TAG, "Updating data");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = String.format("%s/data/%s", MESHBLU_URL, uuid);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, message, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) { emit(SEND_DATA); }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error sending data", volleyError);
                emit(ERROR, volleyError);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Meshblu.this.getHeaders();
            }
        };

        queue.add(request);
    }
    public void deleteDevice(String uuid) {
        Log.d(TAG, "Deleting device");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = String.format("%s/devices/%s", MESHBLU_URL, uuid);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) { emit(DELETE_DEVICE); }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error deleting device", volleyError);
                emit(ERROR, volleyError);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Meshblu.this.getHeaders();
            }
        };

        queue.add(request);
    }
    public void devices(SaneJSONObject options) {
        Log.d(TAG, "Getting devices");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = String.format("%s/v2/devices", MESHBLU_URL);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, options, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) { emit(GET_DEVICE); }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error getting devices", volleyError);
                emit(ERROR, volleyError);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Meshblu.this.getHeaders();
            }
        };

        queue.add(request);
    }
    public void getData(String uuid, SaneJSONObject options) {
        Log.d(TAG, "Getting data");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = String.format("%s/data/%s", MESHBLU_URL, uuid);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, options, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) { emit(GET_DATA, jsonObject); }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error getting data", volleyError);
                emit(ERROR, volleyError);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Meshblu.this.getHeaders();
            }
        };

        queue.add(request);
    }
    public void getPublicKey(String uuid) {
        Log.d(TAG, "Getting public key");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = String.format("%s/devices/%s/publickey", MESHBLU_URL, uuid);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) { emit(GET_PUBLIC_KEY, jsonObject); }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error getting public key", volleyError);
                emit(ERROR, volleyError);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Meshblu.this.getHeaders();
            }
        };

        queue.add(request);
    }
    public void resetToken(String uuid) {
        Log.d(TAG, "Resetting token");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = String.format("%s/devices/%s/token", MESHBLU_URL, uuid);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) { emit(RESET_TOKEN); }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error resetting token", volleyError);
                emit(ERROR, volleyError);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Meshblu.this.getHeaders();
            }
        };

        queue.add(request);
    }
    public void updateDangerously(String uuid, SaneJSONObject properties) {
        Log.d(TAG, "Dangerously updating device");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = String.format("%s/v2/devices/%s", MESHBLU_URL, uuid);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, properties, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) { emit(UPDATE_DEVICE, jsonObject); }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error updating dangerously", volleyError);
                emit(ERROR, volleyError);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Meshblu.this.getHeaders();
            }
        };

        queue.add(request);
    }

    private Map<String, String> getHeaders(){
        Map<String, String>  params = new HashMap<String, String>();
        params.put("meshblu_auth_uuid", Meshblu.this.uuid);
        params.put("meshblu_auth_token", Meshblu.this.token);
        return params;
    }
}
