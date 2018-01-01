using Android.App;
using Android.OS;
using Android.Support.V7.Widget;
using Android.Views;
using CommandCenter.Xamarain.Droid.Adapters;
using CommandCenter.Xamarain.Models;
using System.Collections.Generic;

namespace CommandCenter.Xamarain.Droid
{
    public class GarageDoorFragment : Fragment
    {
        public override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Create your fragment here
        }

        public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            var view = inflater.Inflate(Resource.Layout.fragment_garagedoor_list, container, false);

            // Set the adapter
            if (view.GetType() == typeof(RecyclerView))
            {
                var context = view.Context;
                var recyclerView = (RecyclerView)view;
                recyclerView.SetLayoutManager(new LinearLayoutManager(context));

                var garageDoors = new List<GarageDoor>();

                var leftDoor = new GarageDoor()
                {
                    DoorSide = "Left",
                    Car = "Pilot",
                    Service = RemoteServices.LeftGarageDoor
                };

                garageDoors.Add(leftDoor);

                var rightDoor = new GarageDoor()
                {
                    DoorSide = "Right",
                    Car = "Outlander",
                    Service = RemoteServices.RightGarageDoor
                };

                garageDoors.Add(rightDoor);

                recyclerView.SetAdapter(new MyGarageDoorRecyclerViewAdapter(context, garageDoors));
            }

            return view;
        }
    }
}