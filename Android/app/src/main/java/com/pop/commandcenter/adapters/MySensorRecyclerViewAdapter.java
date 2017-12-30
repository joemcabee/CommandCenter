package com.pop.commandcenter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pop.commandcenter.R;
import com.pop.commandcenter.models.Sensor;

import java.util.List;

/**
 * Created by joemc on 12/26/2017.
 */

public class MySensorRecyclerViewAdapter extends RecyclerView.Adapter<MySensorRecyclerViewAdapter.ViewHolder> {
    private final List<Sensor> mValues;
    Context mContext;

    public MySensorRecyclerViewAdapter(Context context, List<Sensor> items) {
        mContext = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_sensor, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mSensorLocation.setText(mValues.get(position).getLocation());
        holder.mSensorType.setText(mValues.get(position).getType());
        holder.mSensorValue.setText(mValues.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void updateStatus(int position, String value) {
        mValues.get(position).setValue(value);
        notifyItemChanged(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mSensorLocation;
        public final TextView mSensorType;
        public final TextView mSensorValue;
        public Sensor mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mSensorLocation = (TextView) view.findViewById(R.id.sensor_location);
            mSensorType = (TextView) view.findViewById(R.id.sensor_type);
            mSensorValue = (TextView) view.findViewById(R.id.sensor_value);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mSensorLocation.getText() + "' '" + mSensorType.getText() + "'";
        }
    }
}
