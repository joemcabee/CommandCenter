//using Android.Gms.Common;
//using Firebase.Messaging;
//using Firebase.Iid;
using Android.Util;
using System;
using Android.App;
using Android.Content.Res;
using Android.OS;
using Android.Runtime;
using Android.Support.Design.Widget;
using Android.Support.V4.View;
using Android.Support.V4.Widget;
using Android.Support.V7.App;
using Android.Views;
//using Firebase;

namespace CommandCenter.Xamarain.Droid
{
    [Activity(Label = "Command Center", MainLauncher = true, Icon = "@mipmap/ic_launcher")]
    public class MainActivity : AppCompatActivity, NavigationView.IOnNavigationItemSelectedListener, FragmentManager.IOnBackStackChangedListener
    {
        const string TAG = "MainActivity";
        private DrawerLayout mDrawerLayout;
        private NavigationView mNavView;
        private ActionBarDrawerToggle mDrawerToggle;
        internal string mDrawerTitle;
        internal string mTitle;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.Main);

            mTitle = Resources.GetString(Resource.String.system_overview);
            mDrawerTitle = Resources.GetString(Resource.String.app_name);

            mDrawerLayout = FindViewById<DrawerLayout>(Resource.Id.drawer_layout);
            mNavView = FindViewById<NavigationView>(Resource.Id.nav_view);

            // set a custom shadow that overlays the main content when the drawer opens
            mDrawerLayout.SetDrawerShadow(Resource.Drawable.drawer_shadow, GravityCompat.Start);
            
            // enable ActionBar app icon to behave as action to toggle nav drawer
            SupportActionBar.SetDisplayHomeAsUpEnabled(true);
            SupportActionBar.SetHomeButtonEnabled(true);

            // ActionBarDrawerToggle ties together the the proper interactions
            // between the sliding drawer and the action bar app icon
            mDrawerToggle = new CustomActionBarDrawerToggle(
                    this,                  /* host Activity */
                    mDrawerLayout,         /* DrawerLayout object */
                    Resource.String.drawer_open,  /* "open drawer" description for accessibility */
                    Resource.String.drawer_close  /* "close drawer" description for accessibility */
            );        

            mDrawerLayout.SetDrawerListener(mDrawerToggle);

            mNavView.SetNavigationItemSelectedListener(this);
            FragmentManager.AddOnBackStackChangedListener(this);

            if (savedInstanceState == null)
            {
                ShowSystemOverview();
            }

            if (Intent.Extras != null)
            {
                foreach (var key in Intent.Extras.KeySet())
                {
                    var value = Intent.Extras.GetString(key);
                    Log.Debug(TAG, "Key: {0} Value: {1}", key, value);
                }
            }

            //var playAvailable = IsPlayServicesAvailable();

            //if (playAvailable)
            //{
            //    FirebaseApp.InitializeApp(this);
            //    Log.Debug(TAG, "InstanceID token: " + FirebaseInstanceId.Instance.Token);
            //    Log.Debug(TAG, "google app id: " + Resource.String.google_app_id);
            //}
        }

        public override void OnBackPressed()
        {
            bool drawerOpen = mDrawerLayout.IsDrawerOpen(mNavView);

            if (drawerOpen)
            {
                mDrawerLayout.CloseDrawer(mNavView);
            }
            else
            {
                base.OnBackPressed();
                InvalidateOptionsMenu(); // refresh options menu
            }
        }

        public override bool OnCreateOptionsMenu(IMenu menu)
        {
            MenuInflater.Inflate(Resource.Menu.main, menu);

            return base.OnCreateOptionsMenu(menu);
        }

        public override bool OnPrepareOptionsMenu(IMenu menu)
        {
            var sysOverview = Resources.GetString(Resource.String.system_overview);
            var title = SupportActionBar.Title;

            var isSystemOverview = title.Equals(sysOverview);

            // If the nav drawer is open, hide action items related to the content view
            var drawerOpen = mDrawerLayout.IsDrawerOpen(mNavView);

            menu.FindItem(Resource.Id.action_refresh)
                .SetVisible(!drawerOpen && isSystemOverview);

            return base.OnPrepareOptionsMenu(menu);
        }

        public override bool OnOptionsItemSelected(IMenuItem item)
        {
            // The action bar home/up action should open or close the dra.
            // ActionBarDrawerToggle will take care of this.
            if (mDrawerToggle.OnOptionsItemSelected(item))
            {
                return true;
            }

            // Handle action buttons
            switch (item.ItemId)
            {
                case Resource.Id.action_refresh:
                    ShowSystemOverview();
                    return true;
                default:
                    return base.OnOptionsItemSelected(item);
            }
        }

        private void ShowSystemOverview()
        {
            // update the main content by replacing fragments
            Fragment fragment = new NodeFragment();
            
            FragmentManager.BeginTransaction()
                    .Replace(Resource.Id.content_frame, fragment)
                    .Commit();

            // update selected item and title, then close the drawer
            mNavView.SetCheckedItem(Resource.Id.nav_system_overview);
            SetTitle(Resource.String.system_overview);
            mDrawerLayout.CloseDrawer(mNavView);
        }

        public void SetTitle(string title)
        {
            mTitle = title;
            SupportActionBar.Title = title;
        }

        public override void SetTitle(int titleId)
        {
            mTitle = Resources.GetString(titleId);
            SupportActionBar.SetTitle(titleId);
        }

        /**
         * When using the ActionBarDrawerToggle, you must call it during
         * onPostCreate() and onConfigurationChanged()...
         */
        protected override void OnPostCreate(Bundle savedInstanceState)
        {
            base.OnPostCreate(savedInstanceState);
            // Sync the toggle state after onRestoreInstanceState has occurred.
            mDrawerToggle.SyncState();
        }

        public override void OnConfigurationChanged(Configuration newConfig)
        {
            base.OnConfigurationChanged(newConfig);
            // Pass any configuration change to the drawer toggles
            mDrawerToggle.OnConfigurationChanged(newConfig);
        }

        public bool OnNavigationItemSelected(IMenuItem menuItem)
        {
            if (menuItem.IsChecked)
            {
                mDrawerLayout.CloseDrawer(mNavView);
                return false;
            }

            Fragment fragment = null;
            var titleId = -1;

            switch (menuItem.ItemId)
            {
                case (Resource.Id.nav_system_overview):
                    fragment = new NodeFragment();
                    titleId = Resource.String.system_overview;
                    break;
                case (Resource.Id.nav_garage_doors):
                    fragment = new GarageDoorFragment();
                    titleId = Resource.String.doors;
                    break;
                case (Resource.Id.nav_garage_webcam):
                    fragment = new WebViewFragment(Urls.GarageWebcam);
                    titleId = Resource.String.webcam;
                    break;
                case (Resource.Id.nav_guest_room_sensors):
                    fragment = new SensorFragment();
                    titleId = Resource.String.sensors;
                    break;
                case (Resource.Id.nav_guest_room_phat_sensors):
                    fragment = new EnviroFragment();
                    titleId = Resource.String.phat_sensors;
                    break;
                case (Resource.Id.nav_robot_webcam):
                    fragment = new WebViewFragment(Urls.RobotWebcam);
                    titleId = Resource.String.webcam;
                    break;
            }

            FragmentManager.BeginTransaction()
                    .Replace(Resource.Id.content_frame, fragment)
                    .AddToBackStack(null)
                    .Commit();
            
            mNavView.SetCheckedItem(menuItem.ItemId);            
            mDrawerLayout.CloseDrawer(mNavView);

            SetTitle(titleId);
            InvalidateOptionsMenu(); // refresh options menu

            return false;
        }

        public void OnBackStackChanged()
        {
            var fragment = FragmentManager.FindFragmentById(Resource.Id.content_frame);
            var fragmentType = fragment.ToString();
            int title = 0;
            int menuItem = 0;

            if (fragmentType.StartsWith("Node"))
            {
                title = Resource.String.system_overview;
                menuItem = Resource.Id.nav_system_overview;
            }
            else if (fragmentType.StartsWith("Garage"))
            {
                title = Resource.String.doors;
                menuItem = Resource.Id.nav_garage_doors;
            }
            else if (fragmentType.StartsWith("Web"))
            {
                title = Resource.String.webcam;

                string url = fragment.Arguments.GetString("URL").ToLower();

                if (url.Contains("garage"))
                {
                    menuItem = Resource.Id.nav_garage_webcam;
                }
                else if (url.Contains("robot"))
                {
                    menuItem = Resource.Id.nav_robot_webcam;
                }
            }
            else if (fragmentType.StartsWith("Sensor"))
            {
                title = Resource.String.sensors;
                menuItem = Resource.Id.nav_guest_room_sensors;
            }
            else if (fragmentType.StartsWith("Enviro"))
            {
                title = Resource.String.phat_sensors;
                menuItem = Resource.Id.nav_guest_room_phat_sensors;
            }

            if (title > 0)
            {
                SetTitle(title);
            }

            if (menuItem > 0)
            {
                var navView = FindViewById<NavigationView>(Resource.Id.nav_view);
                navView.SetCheckedItem(menuItem);
            }
        }

        //public bool IsPlayServicesAvailable()
        //{
        //    int resultCode = GoogleApiAvailability.Instance.IsGooglePlayServicesAvailable(this);

        //    if (resultCode != ConnectionResult.Success)
        //    {
        //        if (GoogleApiAvailability.Instance.IsUserResolvableError(resultCode))
        //        {
        //            System.Diagnostics.Debug.WriteLine(GoogleApiAvailability.Instance.GetErrorString(resultCode));
        //        }
        //        else
        //        {
        //            System.Diagnostics.Debug.WriteLine("Google Play Services is NOT available.");
        //        }

        //        return false;
        //    }
        //    else
        //    {
        //        System.Diagnostics.Debug.WriteLine("Google Play Services is available.");

        //        return true;
        //    }
        //}
    }

    internal class CustomActionBarDrawerToggle : ActionBarDrawerToggle
    {
        MainActivity owner;

        public CustomActionBarDrawerToggle(MainActivity activity, DrawerLayout layout, int openRes, int closeRes)
            : base(activity, layout, openRes, closeRes)
        {
            owner = activity;
        }

        public override void OnDrawerClosed(View drawerView)
        {
            owner.SetTitle(owner.mTitle);
            owner.InvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }

        public override void OnDrawerOpened(View drawerView)
        {
            owner.SupportActionBar.Title = owner.mDrawerTitle;
            owner.InvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }
    }
}

