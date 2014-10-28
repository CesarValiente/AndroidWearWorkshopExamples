package com.cesarvaliente.androirwearnotifications.ui.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.cesarvaliente.androirwearnotifications.R;
import com.cesarvaliente.androirwearnotifications.ui.fragments.NavigationDrawerFragment;
import com.cesarvaliente.androirwearnotifications.ui.fragments.notifications.BaseNotificationFragment;
import com.cesarvaliente.androirwearnotifications.ui.fragments.notifications.BasicNotificationFragment;
import com.cesarvaliente.androirwearnotifications.ui.fragments.notifications.PagesNotificationFragment;
import com.cesarvaliente.androirwearnotifications.ui.fragments.notifications.StackingNotificationFragment;
import com.cesarvaliente.androirwearnotifications.ui.fragments.notifications.VoiceNotificationFragment;


public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private BaseNotificationFragment mFragment;

    private ActionBar mActionBar;
    private String mOldTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        setActionBar();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        switch (position) {
        case 0:
            mFragment = BasicNotificationFragment.newInstance();
            break;
        case 1:
            mFragment = VoiceNotificationFragment.newInstance();
            break;
        case 2:
            mFragment = PagesNotificationFragment.newInstance();
            break;
        case 3:
            mFragment = StackingNotificationFragment.newInstance();
            break;
        }

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                       .replace(R.id.container, mFragment)
                       .commit();
    }

    public void setActionBar() {
        mActionBar = getActionBar();
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setTitle(getString(R.string.app_name));
    }

    public void setActionBarTitle(String title) {
        mActionBar.setTitle(title);
        mOldTitle = title;
    }

    public void setActionBarOldTitle() {
        if (mOldTitle != null) {
            setActionBarTitle(mOldTitle);
        }
    }

    public void onNotifyMeClick(View view) {
        mFragment.showNotification();
    }

}
