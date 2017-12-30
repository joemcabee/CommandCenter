package com.pop.commandcenter.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pop.commandcenter.clients.ApiClient;
import com.pop.commandcenter.R;
import com.pop.commandcenter.constants.RemoteUrls;
import com.pop.commandcenter.models.GarageDoor;
import com.pop.commandcenter.models.ServiceRequest;
import com.pop.commandcenter.models.ServiceResponse;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link GarageDoor}.
 */
public class MyGarageDoorRecyclerViewAdapter extends RecyclerView.Adapter<MyGarageDoorRecyclerViewAdapter.ViewHolder> {

    private final List<GarageDoor> mValues;
    ProgressBar mProgressBar;
    Context mContext;

    public MyGarageDoorRecyclerViewAdapter(Context context, List<GarageDoor> items) {
        mContext = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_garagedoor, parent, false);

        mProgressBar = (ProgressBar) view.findViewById(R.id.indeterminate_bar);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mSideView.setText(mValues.get(position).getDoorSide());
        holder.mCarView.setText(mValues.get(position).getCar());

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                new TriggerGarageDoorTask(mContext, mProgressBar).execute(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mSideView;
        public final TextView mCarView;
        public final ImageView mImageView;
        public final ProgressBar mProgressView;
        public GarageDoor mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mSideView = (TextView) view.findViewById(R.id.garage_door_side);
            mCarView = (TextView) view.findViewById(R.id.garage_door_car);
            mImageView = (ImageView) view.findViewById(R.id.garage_trigger_icon);
            mProgressView = (ProgressBar) view.findViewById(R.id.indeterminate_bar);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mSideView.getText() + "'";
        }
    }

    private class TriggerGarageDoorTask extends AsyncTask<GarageDoor, GarageDoor, Void> {
        private ProgressBar mProgressBar;
        private Context mContext;

        public TriggerGarageDoorTask(Context context, ProgressBar progressBar){
            this.mContext = context;
            this.mProgressBar = progressBar;
        }

        protected Void doInBackground(GarageDoor... doors) {
            ServiceRequest request = new ServiceRequest();
            request.setUrl(RemoteUrls.CommandCenter);
            request.setService(doors[0].getService());

            ApiClient client = new ApiClient();
            ServiceResponse response = client.httpPost(mContext, request);

            publishProgress(doors[0]);

            return null;
        }

        @Override
        protected void onProgressUpdate(GarageDoor... values) {
            mProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(mContext, "Action complete for " + values[0].toString(), Toast.LENGTH_SHORT);
        }
    }
}
