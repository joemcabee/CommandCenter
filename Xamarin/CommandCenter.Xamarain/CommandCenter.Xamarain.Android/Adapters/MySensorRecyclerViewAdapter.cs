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
    public class MySensorRecyclerViewAdapter : RecyclerView.Adapter
    {
        List<Sensor> mValues;

        public MySensorRecyclerViewAdapter(Context context, List<Sensor> Sensors)
        {
            mValues = Sensors;
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
            MySensorViewHolder vh = holder as MySensorViewHolder;
            vh.mLocationView.Text = mValues[position].Location;
            vh.mTypeView.Text = mValues[position].Type;
            vh.mValueView.Text = mValues[position].Value;
        }

        public override RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.From(parent.Context)
                .Inflate(Resource.Layout.fragment_sensor, parent, false);

            return new MySensorViewHolder(view);
        }

        public void UpdateStatus(int position, String value)
        {
            mValues[position].Value = value;
            NotifyItemChanged(position);
        }
    }

    public class MySensorViewHolder : RecyclerView.ViewHolder
    {
        public View mView;
        public TextView mLocationView;
        public TextView mTypeView;
        public TextView mValueView;
        public Sensor mItem;

        public MySensorViewHolder(View view) : base(view)
        {
            mView = view;
            mLocationView = view.FindViewById<TextView>(Resource.Id.sensor_location);
            mTypeView = view.FindViewById<TextView>(Resource.Id.sensor_type);
            mValueView = view.FindViewById<TextView>(Resource.Id.sensor_value);
        }

        public override string ToString()
        {
            return base.ToString() + " '" + mLocationView.Text + "': " + mTypeView.Text;
        }
    }
}