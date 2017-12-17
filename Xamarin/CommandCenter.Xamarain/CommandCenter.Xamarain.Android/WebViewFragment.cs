using Android.App;
using Android.OS;
using Android.Views;
using Android.Webkit;
using System.Collections.Generic;

namespace CommandCenter.Xamarain.Droid
{
    public class WebViewFragment : Fragment
    {
        string ARG_URL = "URL";

        public WebViewFragment(string url)
        {
            this.Arguments = new Bundle();
            this.Arguments.PutString(ARG_URL, url);
        }

        public override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Create your fragment here
        }

        public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            var view = inflater.Inflate(Resource.Layout.fragment_web_view, container, false);
            var url = this.Arguments.GetString(ARG_URL);

            var authValue = Resources.GetString(Resource.String.basic_auth_hash);
            var map = new Dictionary<string, string>();
            map.Add("Authorization", authValue);

            var webview = view.FindViewById<WebView>(Resource.Id.webViewCommandCenter);
            webview.Settings.JavaScriptEnabled = true;
            webview.LoadUrl(url, map);
            webview.SetWebViewClient(new CustomWebViewClient());

            return view;
        }
    }
}