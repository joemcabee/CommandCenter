using Android.App;
using Android.Content;
using Android.OS;
using Android.Support.V7.Widget;
using Android.Views;
using CommandCenter.Xamarain.Droid.Adapters;
using CommandCenter.Xamarain.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace CommandCenter.Xamarain.Droid
{
    public class SensorFragment : Fragment
    {
        /**
         * Mandatory empty constructor for the fragment manager to instantiate the
         * fragment (e.g. upon screen orientation changes).
         */
        public SensorFragment() { }

        public override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
        }

        public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View view = inflater.Inflate(Resource.Layout.fragment_sensor_list, container, false);

            // Set the adapter
            if (view.GetType() == typeof(RecyclerView))
            {
                Context context = view.Context;
                RecyclerView recyclerView = (RecyclerView)view;

                recyclerView.SetLayoutManager(new LinearLayoutManager(context));

                var items = new List<Sensor>();

                var guestRoomTemp = new Sensor()
                {
                    Location = "Guest Room",
                    Type = "Temperature",
                    Value = "getting value...",
                    Service = RemoteServices.Temp,
                    Position = 0
                };

                items.Add(guestRoomTemp);

                var guestRoomLight = new Sensor()
                {
                    Location = "Guest Room",
                    Type = "Light",
                    Value = "getting value...",
                    Service = RemoteServices.Light,
                    Position = 1
                };

                items.Add(guestRoomLight);

                var adapter = new MySensorRecyclerViewAdapter(context, items);
                recyclerView.SetAdapter(adapter);

                items.ForEach(i => CheckStatus(adapter, i));
            }

            return view;
        }

        internal async void CheckStatus(MySensorRecyclerViewAdapter adapter, Sensor Sensor)
        {
            try
            {
                ServiceRequest request = new ServiceRequest()
                {
                    Url = Urls.CommandCenter,
                    Service = Sensor.Service,
                    AuthHash = Resources.GetString(Resource.String.auth_hash)
                };

                var client = new ApiClient();
                var response = await client.Get(request);
                var sensorValue = "-1";

                if (response.Success)
                {

                    if (Sensor.Service == RemoteServices.Temp)
                    {
                        var temp = JsonConvert.DeserializeObject<Temperature>(response.Data);
                        sensorValue = temp.Fahrenheit + " F";
                    }
                    else
                    {
                        var light = JsonConvert.DeserializeObject<Light>(response.Data);
                        var analogValue = 0;

                        Int32.TryParse(light.Photo, out analogValue);

                        if (analogValue == 0)
                        {
                            sensorValue = "100 %";
                        }
                        else if (analogValue == 1023)
                        {
                            sensorValue = "0 %";
                        }
                        else
                        {
                            double percentage = (double)analogValue / 1023;
                            var flipped = System.Math.Round(1 - percentage, 2);
                            sensorValue = flipped * 100 + " %";
                        }
                    }
                }

                Sensor.Value = sensorValue;

                adapter.UpdateStatus(Sensor.Position, Sensor.Value);
            }
            catch(Exception ex)
            {
                //log
            }            
        }
    }
}