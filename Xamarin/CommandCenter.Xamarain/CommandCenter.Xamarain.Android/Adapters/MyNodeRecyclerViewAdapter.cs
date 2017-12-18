using Android.Content;
using Android.Graphics;
using Android.Graphics.Drawables;
using Android.Support.V7.Widget;
using Android.Views;
using Android.Widget;
using CommandCenter.Xamarain.Models;
using System;
using System.Collections.Generic;

namespace CommandCenter.Xamarain.Droid.Adapters
{
    public class MyNodeRecyclerViewAdapter : RecyclerView.Adapter
    {
        List<Node> mValues;
        Drawable mOnline;
        Drawable mOffline;

        public MyNodeRecyclerViewAdapter(Context context, List<Node> nodes)
        {
            mValues = nodes;
            mOnline = context.GetDrawable(Resource.Drawable.ic_checkmark);
            mOffline = context.GetDrawable(Resource.Drawable.ic_cloud_off);
        }

        public override int ItemCount
        {
            get
            {
                return mValues.Count;
            }
        }

        public override void OnBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            MyNodeViewHolder vh = holder as MyNodeViewHolder;
            vh.mNameView.Text = mValues[position].Name;
            vh.mTypeView.Text = mValues[position].Type;
            vh.mAddressView.Text = mValues[position].IpAddress;

            var status = mValues[position].Status;
            vh.mStatusView.Text = status;

            if (status == "Online")
            {
                vh.mStatusView.SetTextColor(Color.Green);
                vh.mImageView.SetImageDrawable(mOnline);
                vh.mImageView.Background = null;
            }
            else if (status == "Offline")
            {
                vh.mStatusView.SetTextColor(Color.Red);
                vh.mImageView.SetImageDrawable(mOffline);
                vh.mImageView.Background = null;
            }
        }

        public override RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.From(parent.Context)
                .Inflate(Resource.Layout.fragment_node, parent, false);

            return new MyNodeViewHolder(view);
        }

        public void UpdateStatus(int position, String status)
        {
            mValues[position].Status = status;
            NotifyItemChanged(position);
        }
    }

    public class MyNodeViewHolder : RecyclerView.ViewHolder
    {
        public View mView;
        public TextView mNameView;
        public TextView mTypeView;
        public TextView mAddressView;
        public TextView mStatusView;
        public ImageView mImageView;
        public Node mItem;

        public MyNodeViewHolder(View view) : base(view)
        {
            mView = view;
            mNameView = view.FindViewById<TextView>(Resource.Id.node_name);
            mTypeView = view.FindViewById<TextView>(Resource.Id.node_type);
            mAddressView = view.FindViewById<TextView>(Resource.Id.node_address);
            mStatusView = view.FindViewById<TextView>(Resource.Id.node_status);
            mImageView = view.FindViewById<ImageView>(Resource.Id.node_status_icon);
        }

        public override string ToString()
        {
            return base.ToString() + " '" + mNameView.Text + "'";
        }
    }
}