package br.org.cesar.knot.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.org.cesar.knot.R;
import br.org.cesar.knot.data.DroneData;
import br.org.cesar.knot.data.DroneDevice;
import br.org.cesar.knot.lib.ThingApi;
import br.org.cesar.knot.lib.ThingList;

public class DataFragment extends Fragment {

    private static final String END_POINT = "http://192.168.15.7:3000";

    private TextView mResultView;
    private View mProgressView;

    private String mId;
    private String mToken;

    private ThingApi mApi = ThingApi.getInstance(END_POINT);

    public DataFragment() {
    }

    /**
     * Returns a new instance of this fragment.
     */
    public static DataFragment newInstance() {
        DataFragment fragment = new DataFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data, container, false);
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

                    DroneData data = new DroneData();
                    data.altitude = 1000;

                    mApi.getDataList(mId, mToken, "41e280ed-bd6a-41fa-8d5a-5c1e0bc6e0a8", new ThingList<>(DroneData.class), new ThingApi.Callback<List<DroneData>>() {
                        @Override
                        public void onSuccess(List<DroneData> result) {
                            mProgressView.animate().alpha(0);
                            mResultView.setText(Arrays.toString(result.toArray()));
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
        DroneDevice device = new DroneDevice();
        mApi.createDevice(device, new ThingApi.Callback<DroneDevice>() {
            @Override
            public void onSuccess(DroneDevice result) {
                mId = result.uuid;
                mToken = result.token;
                mProgressView.animate().alpha(0);
                mResultView.setText(String.format("id: %s\ntoken: %s", result.uuid, result.token));
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
