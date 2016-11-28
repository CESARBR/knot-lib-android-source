package br.org.cesar.knot;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ThingMessageData<T extends ThingData> {

    transient private final ThingApi mThingApi = ThingApi.getInstance();
    @SerializedName("message")
    List<T> mList;

    transient ThingDevice mDevice;

    public ThingMessageData() {
        mList = new ArrayList<>();
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> list) {
        mList = list;
    }

    public void sendMessage(T message) {
        mList.add(0, message);
    }

    @Override
    public String toString() {
        return "ThingDeviceMessage{" +
                "mList=" + Arrays.toString(mList.toArray()) +
                '}';
    }

    public void save() {

        if (mDevice == null) {
            throw new IllegalStateException("Data needs the device to save");
        }

        if (mDevice.mOwner == null) {
            throw new IllegalStateException("Data's device needs the owner to save");
        }

        String owner = mDevice.mOwner.mId;
        String token = mDevice.mOwner.mToken;

        if (mList.isEmpty()) {
            throw new IllegalStateException("No data to save");
        }

        T message = mList.get(0);

        String json = mThingApi.sendMessage(owner, token, Util.toJson(message));

        fetch();
//        Util.fromJson(this, json);
    }


    public void saveAsync(final ThingApi.Callback callback) {
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

    public void fetch() throws IllegalStateException {

        if (mDevice == null) {
            throw new IllegalStateException("Data needs the device to save");
        }

        if (mDevice.mOwner == null) {
            throw new IllegalStateException("Data's device needs the owner to save");
        }

        String owner = mDevice.mOwner.mId;
        String token = mDevice.mOwner.mToken;
        String device = mDevice.mId;

        String json = mThingApi.sendMessage(owner, token, device);

        Util.fromJson(this, json);
    }
}
