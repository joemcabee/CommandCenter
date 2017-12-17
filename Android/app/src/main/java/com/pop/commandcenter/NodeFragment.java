package com.pop.commandcenter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pop.commandcenter.models.Node;
import com.pop.commandcenter.models.ServiceRequest;
import com.pop.commandcenter.models.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class NodeFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NodeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NodeFragment newInstance(int columnCount) {
        NodeFragment fragment = new NodeFragment();
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
        View view = inflater.inflate(R.layout.fragment_node_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            List<Node> items = new ArrayList<>();

            Node cc = new Node();
            cc.setName("pi-cc");
            cc.setType("Raspberry Pi 3");
            cc.setStatus("checking status...");
            cc.setService(Services.CommandCenterPing);
            cc.setPosition(0);

            items.add(cc);

            Node garage = new Node();
            garage.setName("pi-garage");
            garage.setType("Raspberry Pi");
            garage.setStatus("checking status...");
            garage.setService(Services.GaragePing);
            garage.setPosition(1);

            items.add(garage);

            Node robot = new Node();
            robot.setName("pi-robot");
            robot.setType("Raspberry Pi 3");
            robot.setStatus("checking status...");
            robot.setService(Services.RobotPing);
            robot.setPosition(2);

            items.add(robot);

            recyclerView.setAdapter(new MyNodeRecyclerViewAdapter(context, items));

            new CheckStatusTask(getContext(), recyclerView).execute(cc, garage, robot);
        }

        return view;
    }

    private class CheckStatusTask extends AsyncTask<Node, Node, Void> {
        private Context mContext;
        private RecyclerView mRecyclerView;

        public CheckStatusTask(Context context, RecyclerView recyclerView){
            this.mContext = context;
            this.mRecyclerView = recyclerView;
        }

        protected Void doInBackground(Node... nodes) {
            for (int i = 0; i < nodes.length; i++) {
                ServiceRequest request = new ServiceRequest();
                request.setUrl(Urls.CommandCenter);
                request.setService(nodes[i].getService());

                ApiClient client = new ApiClient();
                ServiceResponse response = client.httpGet(getContext(), request);
                String nodeStatus = "Offline";

                if (response.getSuccess()){
                    nodeStatus = "Online";
                }

                nodes[i].setStatus(nodeStatus);

                publishProgress(nodes[i]);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Node... values) {
            MyNodeRecyclerViewAdapter adapter = (MyNodeRecyclerViewAdapter) mRecyclerView.getAdapter();
            Node n = values[0];
            adapter.updateStatus(values[0].getPosition(), values[0].getStatus());
        }
    }
}
