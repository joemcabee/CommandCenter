package com.pop.commandcenter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavView;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = getResources().getString(R.string.system_overview);
        mDrawerTitle = getResources().getString(R.string.app_name);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavView = (NavigationView) findViewById(R.id.nav_view);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mNavView.setNavigationItemSelectedListener(new NavItemClickListener());

        if (savedInstanceState == null) {
            showSystemOverview();
        }

        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener(){
            public void onBackStackChanged() {
                Fragment fragment = getFragmentManager().findFragmentById(R.id.content_frame);
                String fragmentType = fragment.toString();
                int title = 0;
                int menuItem = 0;

                if (fragmentType.startsWith("Node")) {
                    title = R.string.system_overview;
                    menuItem = R.id.nav_system_overview;
                }
                else if (fragmentType.startsWith("Garage")) {
                    title = R.string.doors;
                    menuItem = R.id.nav_garage_doors;
                }
                else if (fragmentType.startsWith("Web")) {
                    title = R.string.webcam;

                    Bundle bundle = fragment.getArguments();
                    String url = bundle.getString("URL");

                    if (url.toLowerCase().contains("garage")) {
                        menuItem = R.id.nav_garage_webcam;
                    }
                    else if (url.toLowerCase().contains("robot")){
                        menuItem = R.id.nav_robot_webcam;
                    }
                }

                if (title > 0) {
                    setTitle(title);
                }

                if (menuItem > 0) {
                    mNavView.setCheckedItem(menuItem);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mNavView);

        if (drawerOpen) {
            mDrawerLayout.closeDrawer(mNavView);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String sysOverview = getResources().getString(R.string.system_overview);
        String title = getSupportActionBar().getTitle().toString();

        boolean isSystemOverview = title.equals(sysOverview);

        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mNavView);
        menu.findItem(R.id.action_refresh)
                .setVisible(!drawerOpen && isSystemOverview);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.action_refresh:
                showSystemOverview();;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class NavItemClickListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId())
            {
                case (R.id.nav_system_overview):
                    fragment = NodeFragment.newInstance(1);
                    break;
                case (R.id.nav_garage_doors):
                    fragment = GarageDoorFragment.newInstance(1);
                    break;
                case (R.id.nav_garage_webcam):
                    fragment = WebViewFragment.newInstance(Urls.GarageWebcam);
                    break;
                case (R.id.nav_robot_webcam):
                    fragment = WebViewFragment.newInstance(Urls.RobotWebcam);
                    break;
            }

            FragmentManager fragmentManager = getFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .addToBackStack(null)
                    .commit();

            mNavView.setCheckedItem(item.getItemId());
            setTitle(item.getTitle());
            mDrawerLayout.closeDrawer(mNavView);
            invalidateOptionsMenu(); // refresh options menu

            return false;
        }
    }

    private void showSystemOverview() {
        // update the main content by replacing fragments
        Fragment fragment = NodeFragment.newInstance(1);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // update selected item and title, then close the drawer
        mNavView.setCheckedItem(R.id.nav_system_overview);
        setTitle(R.string.system_overview);
        mDrawerLayout.closeDrawer(mNavView);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}