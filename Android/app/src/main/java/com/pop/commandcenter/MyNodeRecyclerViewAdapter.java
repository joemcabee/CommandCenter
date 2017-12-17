package com.pop.commandcenter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pop.commandcenter.models.Node;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Node}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyNodeRecyclerViewAdapter extends RecyclerView.Adapter<MyNodeRecyclerViewAdapter.ViewHolder> {

    private final List<Node> mValues;
    private final Drawable mOnline;
    private final Drawable mOffline;

    public MyNodeRecyclerViewAdapter(Context context, List<Node> items) {
        mValues = items;
        mOnline = context.getDrawable(R.drawable.ic_checkmark);
        mOffline = context.getDrawable(R.drawable.ic_cloud_off);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_node, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mTypeView.setText(mValues.get(position).getType());

        String status = mValues.get(position).getStatus();
        holder.mStatusView.setText(status);

        if (status.equals("Online")) {
            holder.mStatusView.setTextColor(Color.GREEN);
            holder.mImageView.setImageDrawable(mOnline);
            holder.mImageView.setBackground(null);
        }
        else if (status.equals("Offline")){
            holder.mStatusView.setTextColor(Color.RED);
            holder.mImageView.setImageDrawable(mOffline);
            holder.mImageView.setBackground(null);
        }
    }

    public void updateStatus(int position, String status) {
        mValues.get(position).setStatus(status);
        notifyItemChanged(position);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mTypeView;
        public final TextView mStatusView;
        public final ImageView mImageView;
        public Node mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.node_name);
            mTypeView = (TextView) view.findViewById(R.id.node_type);
            mStatusView = (TextView) view.findViewById(R.id.node_status);
            mImageView = (ImageView) view.findViewById(R.id.node_status_icon);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
