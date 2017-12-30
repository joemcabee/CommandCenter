package com.pop.commandcenter.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pop.commandcenter.R;
import com.pop.commandcenter.adapters.MySensorRecyclerViewAdapter;
import com.pop.commandcenter.clients.ApiClient;
import com.pop.commandcenter.constants.RemoteServices;
import com.pop.commandcenter.constants.RemoteUrls;
import com.pop.commandcenter.models.Light;
import com.pop.commandcenter.models.Temperature;
import com.pop.commandcenter.models.Sensor;
import com.pop.commandcenter.models.ServiceRequest;
import com.pop.commandcenter.models.ServiceResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SensorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SensorFragment extends Fragment {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    public SensorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param columnCount Parameter 1.
     * @return A new instance of fragment SensorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SensorFragment newInstance(int columnCount) {
        SensorFragment fragment = new SensorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            List<Sensor> items = new ArrayList<>();

            Sensor guestRoomTemp = new Sensor();

            guestRoomTemp.setLocation("Guest Room");
            guestRoomTemp.setType("Temperature");
            guestRoomTemp.setValue("getting value...");
            guestRoomTemp.setService(RemoteServices.Temp);
            guestRoomTemp.setPosition(0);

            items.add(guestRoomTemp);

            Sensor guestRoomLight = new Sensor();

            guestRoomLight.setLocation("Guest Room");
            guestRoomLight.setType("Light");
            guestRoomLight.setValue("getting value...");
            guestRoomLight.setService(RemoteServices.Light);
            guestRoomLight.setPosition(1);

            items.add(guestRoomLight);

            MySensorRecyclerViewAdapter adapter = new MySensorRecyclerViewAdapter(context, items);
            recyclerView.setAdapter(adapter);

            recyclerView.setAdapter(new MySensorRecyclerViewAdapter(getContext(), items));

            new CheckValueTask(getContext(), recyclerView).execute(guestRoomLight, guestRoomTemp);
        }

        return view;
    }

    private class CheckValueTask extends AsyncTask<Sensor, Sensor, Void> {
        private Context mContext;
        private RecyclerView mRecyclerView;

        public CheckValueTask(Context context, RecyclerView recyclerView) {
            this.mContext = context;
            this.mRecyclerView = recyclerView;
        }

        protected Void doInBackground(Sensor... nodes) {
            for (int i = 0; i < nodes.length; i++) {
                ServiceRequest request = new ServiceRequest();
                request.setUrl(RemoteUrls.CommandCenter);
                request.setService(nodes[i].getService());

                String sensorValue = "Offline";

                try {
                    ApiClient client = new ApiClient();
                    ServiceResponse response = client.httpGet(this.mContext, request);

                    if (response.getSuccess()) {
                        JSONObject jsonResponse = new JSONObject(response.getData());

                        if (nodes[i].getService() == RemoteServices.Temp)
                        {
                            sensorValue = jsonResponse.getString("Fahrenheit") + " F";
                        }
                        else
                        {

                            int analogValue = jsonResponse.getInt("Photo");

                            if (analogValue == 0) {
                                sensorValue = "100 %";
                            }
                            else if (analogValue == 1023) {
                                sensorValue = "0 %";
                            }
                            else {
                                float percentage = ((float)analogValue / 1023) * 100;
                                int roundedPercentage = Math.round(percentage);
                                int flipped = 100 - roundedPercentage;
                                sensorValue = flipped * 100 + " %";
                            }
                        }
                    }
                }
                catch (Exception ex) {
                    Log.e("SensorFragment", ex.getMessage());
                    sensorValue = "Error!";
                }

                nodes[i].setValue(sensorValue);

                publishProgress(nodes[i]);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Sensor... values) {
            MySensorRecyclerViewAdapter adapter = (MySensorRecyclerViewAdapter) mRecyclerView.getAdapter();
            Sensor s = values[0];
            adapter.updateStatus(values[0].getPosition(), values[0].getValue());
        }
    }
}