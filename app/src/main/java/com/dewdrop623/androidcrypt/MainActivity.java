package com.dewdrop623.androidcrypt;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    private static final String MAINACTIVITYFRAGMENT_TAG = "com.dewdrop623.androidcrypt.MainActivity.MAINACTIVITYFRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final MainActivityFragment mainActivityFragment;
        if (savedInstanceState == null) {
            mainActivityFragment = new MainActivityFragment();
            attachFragment(mainActivityFragment, false, MAINACTIVITYFRAGMENT_TAG);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            mainActivityFragment = (MainActivityFragment) fragmentManager.findFragmentByTag(MAINACTIVITYFRAGMENT_TAG);
        }


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityFragment.actionButtonPressed();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //choose whether the Floating Action Button should be visible or not
    public void setFabVisible(boolean visible) {
        fab.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    //Called by MainActivityFragment to change the icon when switching between encryption and decryption.
    public void setFABIcon(int drawableId) {
        fab.setImageDrawable(ResourcesCompat.getDrawable(getResources(), drawableId, null));
    }

    /*shows the dialog to help find internal storage in SAF. maybe called by MainActivity fragment or by the Help fragment*/
    public void showMissingFilesHelpDialog() {
        View missingFilesHelpLayout = getLayoutInflater().inflate(R.layout.dialogfragment_missing_files, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(missingFilesHelpLayout);
        builder.show();
    }

    /*
    Called to display things like SettingsFragment and AboutFragment.
     */
    public void displayFragmentScreen(Fragment fragment, String title) {
        setFabVisible(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        attachFragment(fragment, true, null);
        if (title != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    /*
    * Called by MainActivityFragment's onResume. Bring the FAB back, remove the back arrow from the action bar, change the title
    * */
    public void returnedToMainFragment() {
        setFabVisible(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(R.string.app_name);
    }

    /*
    * display the given fragment
     */
    private void attachFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(R.id.main_fragment_container, fragment, tag);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
