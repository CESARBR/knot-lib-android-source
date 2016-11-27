package br.org.cesar.knot;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThingDeviceData<T extends ThingData> {

    transient private final ThingApi mThingApi = ThingApi.getInstance();
    @SerializedName("data")
    List<T> mList;
    transient ThingDevice mDevice;

    public ThingDeviceData() {
        mList = new ArrayList<>();
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> list) {
        mList = list;
    }

    public void addData(T data) {
        mList.add(0, data);
    }

    @Override
    public String toString() {
        return "ThingDeviceData{" +
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
        String device = mDevice.mId;

        if (mList.isEmpty()) {
            throw new IllegalStateException("No data to save");
        }

        T data = mList.get(0);

        String json = mThingApi.createData(owner, token, device, Util.toJson(data));

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

        String json = mThingApi.getDataList(owner, token, device);

//        JSONObject jsonObject = new JSONObject(json);
//        JSONArray jsonArray = jsonObject.getJSONArray("data");

//        Gson gson = new Gson();

//        mList.clear();
//        for(int i = 0, count = jsonArray.length(); i < count; i++){
//            T data = gson.fromJson(jsonArray.getJSONObject(i).toString(), new TypeToken<T>(){}.getType());
//            mList.add(data);
//        }

//        mList = gson.fromJson(jsonArray.toString(), new TypeToken<List<T>>(){}.getType());

//        List<T> object = new Gson().fromJson(jsonObject.getJSONArray("data").toString(), new TypeToken<List<T>>(getList().getClass()){}.getType());

        Util.fromJson(this, json);
    }

}
