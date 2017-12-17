using Android;
using Android.Content;
using Android.Support.V7.Widget;
using Android.Views;
using Android.Widget;
using CommandCenter.Xamarain.Models;
using System;
using System.Collections.Generic;

namespace CommandCenter.Xamarain.Droid.Adapters
{
    public class MyGarageDoorRecyclerViewAdapter : RecyclerView.Adapter
    {
        List<GarageDoor> mValues;
        ProgressBar mProgressBar;
        Context mContext;

        public MyGarageDoorRecyclerViewAdapter(Context context, List<GarageDoor> garageDoors)
        {
            mContext = context;
            mValues = garageDoors;
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
            MyGarageDoorViewHolder vh = holder as MyGarageDoorViewHolder;
            vh.mItem = mValues[position];
            vh.mSideView.Text = (mValues[position].DoorSide);
            vh.mCarView.Text = (mValues[position].Car);
            vh.mImageView.SetOnClickListener(vh);
        }

        public override RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.From(parent.Context)
                    .Inflate(Resource.Layout.fragment_garagedoor, parent, false);

            mProgressBar = view.FindViewById<ProgressBar>(Resource.Id.indeterminate_bar);

            return new MyGarageDoorViewHolder(view);
        }
    }

    public class MyGarageDoorViewHolder : RecyclerView.ViewHolder, View.IOnClickListener
    {
        public View mView;
        public TextView mSideView;
        public TextView mCarView;
        public ImageView mImageView;
        public ProgressBar mProgressView;
        public GarageDoor mItem;

        public MyGarageDoorViewHolder(View view) : base(view)
        {
            mView = view;
            mSideView = view.FindViewById<TextView>(Resource.Id.garage_door_side);
            mCarView = view.FindViewById<TextView>(Resource.Id.garage_door_car);
            mImageView = view.FindViewById<ImageView>(Resource.Id.garage_trigger_icon);
            mProgressView = view.FindViewById<ProgressBar>(Resource.Id.indeterminate_bar);
        }

        public async void OnClick(View v)
        {
            mProgressView.Visibility = ViewStates.Visible;

            var request = new ServiceRequest();
            request.AuthHash = v.Context.GetString(Resource.String.auth_hash);
            request.Service = mItem.Service;

            var client = new ApiClient();
            var response = await client.Post(request);

            if (response.StatusCode == 200)
            {
                Toast.MakeText(v.Context,
                    String.Format("Post Successful for the {0}.", mItem.DoorSide), ToastLength.Short);
            }
            else
            {
                Toast.MakeText(v.Context,
                    String.Format("Oh no! Post unsuccessful for the {0}.", mItem.DoorSide), ToastLength.Short);
            }

            mProgressView.Visibility = ViewStates.Invisible;
        }

        public override string ToString()
        {
            return base.ToString() + " '" + mSideView.Text + "'";
        }
    }
}