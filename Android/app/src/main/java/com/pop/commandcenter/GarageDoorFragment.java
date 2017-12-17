package com.pop.commandcenter;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pop.commandcenter.models.GarageDoor;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class GarageDoorFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GarageDoorFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GarageDoorFragment newInstance(int columnCount) {
        GarageDoorFragment fragment = new GarageDoorFragment();
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
        View view = inflater.inflate(R.layout.fragment_garagedoor_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            List<GarageDoor> garageDoors = new ArrayList<>();

            GarageDoor leftDoor = new GarageDoor();
            leftDoor.setDoorSide("Left");
            leftDoor.setCar("Pilot");
            leftDoor.setService(Services.LeftGarageDoor);

            garageDoors.add(leftDoor);

            GarageDoor rightDoor = new GarageDoor();
            rightDoor.setDoorSide("Right");
            rightDoor.setCar("Outlander");
            rightDoor.setService(Services.RightGarageDoor);

            garageDoors.add(rightDoor);

            recyclerView.setAdapter(new MyGarageDoorRecyclerViewAdapter(getContext(), garageDoors));
        }

        return view;
    }
}
