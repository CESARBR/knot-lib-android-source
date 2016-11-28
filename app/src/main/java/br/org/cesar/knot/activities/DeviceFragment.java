package br.org.cesar.knot.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.org.cesar.knot.R;
import br.org.cesar.knot.data.BroadcastMessage;
import br.org.cesar.knot.data.DroneDevice;
import br.org.cesar.knot.data.WaterFountainData;
import br.org.cesar.knot.lib.KnotException;
import br.org.cesar.knot.lib.ThingApi;
import br.org.cesar.knot.lib.ThingList;
import br.org.cesar.knot.lib.model.AbstractThingMessage;

public class DeviceFragment extends Fragment {

    private static final String END_POINT = "http://192.168.1.10:3000";

    private TextView mResultView;
    private View mProgressView;

    private String mId;
    private String mToken;
    private ThingApi mApi = ThingApi.getInstance(END_POINT);

    public DeviceFragment() {
    }

    /**
     * Returns a new instance of this fragment.//Message
     * String sendMessage(String owner, String token, String messageJson) {
     * final String endPoint = mEndPoint + MESSAGE;
     * Request request = generateBasicRequestBuild(owner, token, endPoint).build();
     * <p>
     * try {
     * Response response = mHttpClient.newCall(request).execute();
     * return response.body().string();
     * } catch (Exception e) {
     * throw new RuntimeException(e.getMessage());
     * }
     * }
     */
    public static DeviceFragment newInstance() {
        DeviceFragment fragment = new DeviceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_device, container, false);
        mResultView = (TextView) rootView.findViewById(R.id.section_result);
        mResultView.setAlpha(0);
        mProgressView = rootView.findViewById(android.R.id.progress);
        View fabView = rootView.findViewById(R.id.fab);
        fabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mId != null && mToken != null) {
                    mProgressView.animate().alpha(1);
                    mResultView.animate().alpha(0);
                    mApi.getDeviceList("baceda87-1c85-4c4e-91e6-9cfca1927841-0000", "244f116a7c43a127b7be71c4ee5f5ec7eb2982df", new ThingList<>(DroneDevice.class), new ThingApi.Callback<List<DroneDevice>>() {
                        @Override
                        public void onSuccess(List<DroneDevice> result) {
                            mProgressView.animate().alpha(0);
                            if (result != null) {
                                mResultView.setText(Arrays.toString(result.toArray()));
                            } else {
                                mResultView.setText("Not found!");
                            }
                            mResultView.animate().alpha(1);
                        }

                        @Override
                        public void onError(Exception error) {
                            mProgressView.animate().alpha(0);
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final BroadcastMessage  message = new BroadcastMessage();
        message.setTest("off");
        message.setMessage("show message");
        message.setTimestamp("12434325235");
        ArrayList<String> devices = new ArrayList<>();
        devices.add("0f486484-6d89-40e9-80f9-6cb4f63fb139-0000");
        devices.add("c83e6d51-d270-454a-9a1d-bda5fcf2d8df-0000");
        message.setDevices(devices);
//        final DroneDevice device = new DroneDevice();
//        mApi.createDevice(device, new ThingApi.Callback<DroneDevice>() {
//            @Override
//            public void onSuccess(DroneDevice result) {
//                mId = result.uuid;
//                mToken = result.token;
//                mProgressView.animate().alpha(0);
//                mResultView.setText(String.format("id: %s\ntoken: %s", result.uuid, result.token));
//                mResultView.animate().alpha(1);
//
//                device.owner = "cb24b61f-e83f-4396-9253-556a05312ab0-0000";


//                        mApi.claimDevice("cb24b61f-e83f-4396-9253-556a05312ab0-0000", "143f569c2c3253db9ff5c98d35a6ec6bad28925f", mId, new ThingApi.Callback<Boolean>() {

//                try {
//                    mApi.getDataList("cb24b61f-e83f-4396-9253-556a05312ab0-0000","143f569c2c3253db9ff5c98d35a6ec6bad28925f","cb24b61f-e83f-4396-9253-556a05312ab0-0000", new ThingList<>(AbstractThingDevice.class,
//                            new Ca);
//                } catch (KnotException e) {
//                    e.printStackTrace();
//                }


//            mApi.updateDevice(mId,mToken,mId,device, new ThingApi.Callback<DroneDevice>() {
//
//                    @Override
//                    public void onSuccess(DroneDevice result) {
//                        mApi.getDevice(mId,mToken,mId,DroneDevice.class, new ThingApi.Callback<DroneDevice>() {
//
//                            @Override
//                            public void onSuccess(DroneDevice result) {
//                                Log.d("emiddio", ""+result.toString());
//
//                            }
//
//                            @Override
//                            public void onError(Exception error) {
//
//                            }
//                        });


//                        WaterFountainData teste = new WaterFountainData();
//                        teste.setFountainId(10);
//                        teste.setWeight(15.00);
//                            mApi.createData("cb24b61f-e83f-4396-9253-556a05312ab0-0000", "143f569c2c3253db9ff5c98d35a6ec6bad28925f", "cb24b61f-e83f-4396-9253-556a05312ab0-0000", teste, new teste());
//                            mApi.createData("cb24b61f-e83f-4396-9253-556a05312ab0-0000", "143f569c2c3253db9ff5c98d35a6ec6bad28925f", "cb24b61f-e83f-4396-9253-556a05312ab0-0000", teste, new teste());
//                            mApi.createData("cb24b61f-e83f-4396-9253-556a05312ab0-0000", "143f569c2c3253db9ff5c98d35a6ec6bad28925f", "cb24b61f-e83f-4396-9253-556a05312ab0-0000", teste, new teste());
//
//
//                        mApi.getDataList("cb24b61f-e83f-4396-9253-556a05312ab0-0000", "143f569c2c3253db9ff5c98d35a6ec6bad28925f", "cb24b61f-e83f-4396-9253-556a05312ab0-0000",
//                                        new ThingList<>(WaterFountainData.class), new Datacallback());


//                        mApi.claimDevice("cb24b61f-e83f-4396-9253-556a05312ab0-0000", "143f569c2c3253db9ff5c98d35a6ec6bad28925f", mId, new ThingApi.Callback<Boolean>() {
//                            @Override
//                            public void onSuccess(Boolean result) {
//                                mApi.getDeviceList("cb24b61f-e83f-4396-9253-556a05312ab0-0000", "143f569c2c3253db9ff5c98d35a6ec6bad28925f",new ThingList<>(DroneDevice.class), new DeviceCallback());
//                            }
//
//                            @Override
//                            public void onError(Exception error) {
//
//                            }
//                        });
//                    }
//                    @Override
//                    public void onError(Exception error) {
//                        Log.d("emidio", "opsospospsopso");
//
//                    }
//                });
//            }
//
//            //
//            @Override
//            public void onError(Exception error) {
//                mProgressView.animate().alpha(0);
//                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//            }
//
//
//        });

        mApi.sendMessage("baceda87-1c85-4c4e-91e6-9cfca1927841-0000", "244f116a7c43a127b7be71c4ee5f5ec7eb2982df", message, new ThingApi.Callback<BroadcastMessage>() {
            @Override
            public void onSuccess(BroadcastMessage result) {
                Log.i("KNOT_NOOW", "result sucess: " + result.toString());
                mProgressView.animate().alpha(0);
                mResultView.setText(result.toString());
                mResultView.animate().alpha(1);
            }

            @Override
            public void onError(Exception error) {
                Log.e("KN_NOOW", "Error: "+error.getMessage(), error);
            }
        });
    }

    class DeviceCallback implements ThingApi.Callback<List<DroneDevice>> {

        @Override
        public void onSuccess(List<DroneDevice> data) {
            Log.d("emiddio", "" + data.size());
        }

        @Override
        public void onError(Exception e) {
            Log.d("emiddio", "erro");

        }
    }

    class teste implements ThingApi.Callback<Boolean> {
        @Override
        public void onSuccess(Boolean result) {

        }

        @Override
        public void onError(Exception error) {

        }
    }

    ;

    class Datacallback implements ThingApi.Callback<List<WaterFountainData>> {

        @Override
        public void onSuccess(List<WaterFountainData> data) {
            Log.d("emiddio", "" + data.size());
        }

        @Override
        public void onError(Exception e) {
            Log.d("emiddio", "erro");

        }
    }


}
