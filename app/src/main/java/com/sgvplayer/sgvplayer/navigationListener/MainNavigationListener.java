package com.sgvplayer.sgvplayer.navigationListener;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.sgvplayer.sgvplayer.FragmentSelector.FragmentSelector;

/**
 * Manages the animation of a toolbar
 * Created by apt_a on 07/07/2016.
 */
public class MainNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private MenuItem  previousMenuItem;
    private FragmentSelector fragmentSelector;

    public  MainNavigationListener(DrawerLayout drawerLayout, FragmentSelector fragmentSelector){
        this.drawerLayout = drawerLayout;
        this.fragmentSelector = fragmentSelector;
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.ç
        collapseBarAnimations(item);

        int fragmentId = item.getItemId();

        fragmentSelector.select(fragmentId);

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void collapseBarAnimations(MenuItem menuItem){
        if(previousMenuItem != null){
            previousMenuItem.setChecked(false);
        }
        previousMenuItem = menuItem;
        if (menuItem.isChecked()){
            menuItem.setChecked(false);
        }else{
            menuItem.setChecked(true);
        }
        drawerLayout.closeDrawers();
    }
}
