package br.org.cesar.knot;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

public class ThingDevice<T extends ThingData> {

    transient private final ThingApi mThingApi = ThingApi.getInstance();

    transient ThingDevice mOwner;
    @SerializedName("uuid")
    String mId;
    @SerializedName("token")
    String mToken;
    private transient ThingDeviceData<T> mData;

    public ThingDevice() {
        mOwner = this;
        mData = new ThingDeviceData<>();
        mData.mDevice = this;
    }

    public ThingDevice(String id, String token) {
        this();
        mId = id;
        mToken = token;
    }

    public ThingDevice getOwner() {
        return mOwner;
    }

    public void setOwner(ThingDevice owner) {
        mOwner = owner;
    }

    public ThingDeviceData<T> getData() {
        return mData;
    }

    public void addData(T data) {
        mData.addData(data);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    @Override
    public String toString() {
        return "ThingDevice{" +
                "mId='" + mId + '\'' +
                ", mToken='" + mToken + '\'' +
                ", mData=" + mData.toString() +
                '}';
    }

    public void save() throws IllegalStateException {
        if (mId == null) {
            // Create
            String json = mThingApi.createDevice();
            Util.fromJson(this, json);
        } else {
            // Update
            if (mOwner == null) {
                throw new IllegalStateException("Device needs the owner to update");
            } else {
                mThingApi.updateDevice(mOwner.mId, mOwner.mToken, mId, Util.toJson(this));
            }
        }
    }

    public void saveAsync(final ThingApi.Callback callback) {
        final Thread currentThread = Thread.currentThread();
        new Thread(currentThread);
        new Thread() {
            @Override
            public void run() {
                try {
                    save();
                    callback.onSuccess();
                } catch (Throwable e) {
                    callback.onError(e);
                }
            }
        }.start();
    }

    public void delete() {
        String owner = mId;
        String token = mToken;

        if (mOwner != null) {
            owner = mOwner.mId;
            token = mOwner.mToken;
        }

        mThingApi.deleteDevice(owner, token, mId);
    }

    public void fetch() throws IllegalStateException {
        if (mId == null) {
            throw new IllegalStateException("Device needs identification to fetch");
        } else {

            String owner = mId;
            String token = mToken;

            if (mOwner != null) {
                owner = mOwner.mId;
                token = mOwner.mToken;
            }

            String json = mThingApi.getDeviceList(owner, token, mId);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("devices");
            Util.fromJson(this, jsonArray.getJSONObject(0).toString());
        }
    }

    public void fetchAsync(final ThingApi.Callback callback) {
        new Thread() {
            @Override
            public void run() {
                try {
                    fetch();
                    callback.onSuccess();
                } catch (Throwable e) {
                    callback.onError(e);
                }
            }
        }.start();
    }

}
