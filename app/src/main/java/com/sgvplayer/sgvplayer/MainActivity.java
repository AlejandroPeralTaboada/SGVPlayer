package com.sgvplayer.sgvplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

//Added by Alvaro:
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;
import com.sgvplayer.sgvplayer.model.fileNavigator.Mp3File;

import com.sgvplayer.sgvplayer.FragmentSelector.FragmentSelector;
import com.sgvplayer.sgvplayer.navigationListener.MainNavigationListener;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements
        MusicFragment.OnFragmentInteractionListener,
        PlayerFragment.OnFragmentInteractionListener,
        ClassifierFragment.OnFragmentInteractionListener,
        AllSongsFragment.OnListFragmentInteractionListener,
        FragmentSelector {


    private static final int READ_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MainNavigationListener(drawer, this));

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            MusicTabHostFragment firstFragment = new MusicTabHostFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
        }

        checkPermissions();
    }


    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, R.string.storage_permissions, Toast.LENGTH_LONG).show();
                }
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READ_EXTERNAL_STORAGE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                //TODO cambiar esta basura
                Toast.makeText(this, "IDIOTA TENIAS QUE ACEPTARLO MANCO", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //All Fragment listeners should go here:

    @Override
    public void onFragmentInteraction() {
        //do stuff
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

        //do stuff
    }

    @Override
    public void onListFragmentInteraction(List<Mp3File> mp3Files, int index){
        startPlayerFragment(mp3Files, index);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*
    @Override
    public void onBackPressed(){
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0){
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)){
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } else {
            getFragmentManager().popBackStack();
        }
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /*
        if (id == R.id.action_settings) {
            return true;
        }
       */

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void select(int id) {
        switch (id) {
            case R.id.nav_music:
                moveToMusicTabHostFragment();
                break;
            case R.id.nav_classifier:
                moveToClassifierFragment();
                break;
            case R.id.nav_settings:
                break;
            default:
        }
    }

    MusicTabHostFragment musicTabHostFragment;
    private void moveToMusicTabHostFragment(){
        musicTabHostFragment = new MusicTabHostFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, musicTabHostFragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    private void moveToClassifierFragment() {
        // Create fragment
        ClassifierFragment newFragment = new ClassifierFragment();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        //transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void startPlayerFragment(List<Mp3File> mp3File, int index){
        PlayerFragment newFragment =PlayerFragment.newInstance((Serializable) mp3File,index);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container, newFragment).commit();

        //FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.fragment_container, newFragment).commit();
        // Send the (@link Mp3File) selected. See:
        // http://stackoverflow.com/questions/13445594/data-sharing-between-fragments-and-activity-in-android
        // http://stackoverflow.com/questions/17436298/how-to-pass-a-variable-from-activity-to-fragment-and-pass-it-back
        // http://stackoverflow.com/questions/21093809/pass-custom-class-to-fragment
    }

    @Override
    public void onFragmentInteraction(int id) {

    }
}