using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.Support.V7.Widget;
using CommandCenter.Xamarain.Models;
using Java.Lang;
using CommandCenter.Xamarain.Droid.Adapters;

namespace CommandCenter.Xamarain.Droid
{
    public class NodeFragment : Fragment
    {
        /**
         * Mandatory empty constructor for the fragment manager to instantiate the
         * fragment (e.g. upon screen orientation changes).
         */
        public NodeFragment() { }

        public override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
        }

        public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View view = inflater.Inflate(Resource.Layout.fragment_node_list, container, false);

            // Set the adapter
            if (view.GetType() == typeof(RecyclerView))
            {
                Context context = view.Context;
                RecyclerView recyclerView = (RecyclerView)view;

                recyclerView.SetLayoutManager(new LinearLayoutManager(context));

                var items = new List<Node>();

                var cc = new Node()
                {
                    Name = "pi-cc",
                    Type = "Raspberry Pi 3",
                    Status = "checking status...",
                    Service = Services.CommandCenterPing,
                    Position = 0,
                    IpAddress = "192.168.0.200"
                };

                items.Add(cc);

                var garage = new Node()
                {
                    Name = "pi-garage",
                    Type = "Raspberry Pi",
                    Status = "checking status...",
                    Service = Services.GaragePing,
                    Position = 1,
                    IpAddress = "192.168.0.201"
                };

                items.Add(garage);

                var robot = new Node()
                {
                    Name = "pi-robot",
                    Type = "Raspberry Pi 3",
                    Status = "checking status...",
                    Service = Services.RobotPing,
                    Position = 2,
                    IpAddress = "192.168.0.202"
                };

                items.Add(robot);

                var sensor = new Node()
                {
                    Name = "pi-sensor",
                    Type = "Raspberry Pi Zero-W",
                    Status = "checking status...",
                    Service = Services.SensorPing,
                    Position = 3,
                    IpAddress = "192.168.0.203"
                };

                items.Add(sensor);

                var enviro = new Node()
                {
                    Name = "pi-enviro",
                    Type = "Raspberry Pi Zero-W",
                    Status = "checking status...",
                    Service = Services.EnviroPing,
                    Position = 4,
                    IpAddress = "192.168.0.204"
                };

                items.Add(enviro);

                var adapter = new MyNodeRecyclerViewAdapter(context, items);
                recyclerView.SetAdapter(adapter);

                items.ForEach(i => CheckStatus(adapter, i));
                //new CheckStatusTask(this.Context, recyclerView).Execute(cc, garage, robot);
            }

            return view;
        }

        internal async void CheckStatus(MyNodeRecyclerViewAdapter adapter, Node node)
        {
            ServiceRequest request = new ServiceRequest()
            {
                Url = Urls.CommandCenter,
                Service = node.Service,
                AuthHash = Resources.GetString(Resource.String.auth_hash)
            };

            var client = new ApiClient();
            var response = await client.Get(request);
            var nodeStatus = "Offline";

            if (response.Success)
            {
                nodeStatus = "Online";
            }

            node.Status = nodeStatus;
            
            adapter.UpdateStatus(node.Position, node.Status);
        }
    }
}