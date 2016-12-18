package br.org.cesar.knot;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class ThingApi {

    private static final String EMPTY_JSON = "{}";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String HEADER_AUTH_UUID = "meshblu_auth_uuid";
    private static final String HEADER_AUTH_TOKEN = "meshblu_auth_token";
    private static final String WHOAMI = "/v2/whoami/";
    private static final String DATA_PATH = "/data/";
    private static final String DEVICE_PATH = "/devices/";
    private static final String MESSAGE = "/messages";
    private static final String DEVICE_PROPERTY_PATH_GATEWAY = "/gateway/";
    private static ThingApi sInstance;
    private final OkHttpClient mHttpClient;
    private String mEndPoint;

    private ThingApi() {
        mHttpClient = new OkHttpClient();
    }

    public static void initialize(String endPoint) {
        getInstance().mEndPoint = endPoint;
    }

    static ThingApi getInstance() {
        if (sInstance == null) {
            sInstance = new ThingApi();
        }
        return sInstance;
    }

    String getWhoAmI(String owner, String token) {
        final String endPoint = mEndPoint + WHOAMI;
        Request request = generateBasicRequestBuild(owner, token, endPoint).build();

        try {
            Response response = mHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    String createDevice() {
        final String endPoint = mEndPoint + DEVICE_PATH;
        RequestBody body = createEmptyRequestBody();
        Request request = generateBasicRequestBuild(endPoint).post(body).build();

        try {
            Response response = mHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Device methods

    String updateDevice(String owner, String token, String id, String json) {
        final String endPoint = mEndPoint + DEVICE_PATH + id;
        RequestBody body = createRequestBodyWith(json);
        Request request = generateBasicRequestBuild(owner, token, endPoint).put(body).build();

        try {
            Response response = mHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    boolean deleteDevice(String owner, String token, String device) {
        final String endPoint = mEndPoint + DEVICE_PATH + device;
        Request request = generateBasicRequestBuild(owner, token, endPoint).delete().build();

        try {
            Response response = mHttpClient.newCall(request).execute();
            // TODO: 15/12/15 Please verify the response.body().string() value to check if the device was deleted
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    String getDeviceGateway(String owner, String token, String device) {
        final String endPoint = mEndPoint + DEVICE_PATH + device + DEVICE_PROPERTY_PATH_GATEWAY;
        Request request = generateBasicRequestBuild(owner, token, endPoint).build();

        try {
            Response response = mHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    String getDeviceList(String owner, String token, String device) {
        final String endPoint = mEndPoint + DEVICE_PATH + device;
        Request request = generateBasicRequestBuild(owner, token, endPoint).build();

        try {
            Response response = mHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    String createData(String owner, String token, String device, String jsonData) {
        final String endPoint = mEndPoint + DATA_PATH + device;
        RequestBody body = createRequestBodyWith(jsonData);
        Request request = generateBasicRequestBuild(owner, token, endPoint).post(body).build();

        try {
            Response response = mHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Data methods

    String getDataList(String owner, String token, String device) {
        final String endPoint = mEndPoint + DATA_PATH + device;
        Request request = generateBasicRequestBuild(owner, token, endPoint).build();

        try {
            Response response = mHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //Message
    String sendMessage(String owner, String token, String messageJson) {
        final String endPoint = mEndPoint + MESSAGE;
        Request request = generateBasicRequestBuild(owner, token, endPoint).build();

        try {
            Response response = mHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private RequestBody createEmptyRequestBody() {
        return RequestBody.create(JSON, EMPTY_JSON);
    }

    private RequestBody createRequestBodyWith(String data) {
        return RequestBody.create(JSON, data);
    }

    private Request.Builder generateBasicRequestBuild(String uuid, String token, String endPoint) {
        return new Request.Builder()
                .addHeader(HEADER_AUTH_UUID, uuid)
                .addHeader(HEADER_AUTH_TOKEN, token)
                .url(endPoint);
    }

    private Request.Builder generateBasicRequestBuild(String endPoint) {
        return new Request.Builder().url(endPoint);
    }

    public interface Callback {

        void onSuccess();

        void onError(Throwable error);
    }
}
