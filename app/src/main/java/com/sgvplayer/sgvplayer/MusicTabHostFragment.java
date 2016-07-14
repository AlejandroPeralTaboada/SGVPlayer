package com.sgvplayer.sgvplayer;


import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TextView;


import com.sgvplayer.sgvplayer.MusicFragment.OnFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

public class MusicTabHostFragment extends Fragment implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener, OnFragmentInteractionListener {

    ViewPager viewPager;
    TabHost tabHost;
    View view;

    public static int ALL_SONGS = 1;
    public static int ARTIST = 2;
    public static int ALBUM = 3;
    public static int GENRES = 4;
    public static int PLAYLISTS = 5;
    public static int FOLDERS = 6;

    public MusicTabHostFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_music_tab_host, container, false);

        initTabHost();
        initViewPager();
        return view;

    }

    private void initTabHost() {
        tabHost = (TabHost) view.findViewById(R.id.tabHost);
        tabHost.setup();
        Resources resources = getResources();
        String[] tabNames = resources.getStringArray(R.array.tabs_name);

        for (String s : tabNames) {

            View tabView =LayoutInflater.from(getActivity()).inflate(R.layout.tabs_layout,tabHost.getTabWidget(),false);
           ((TextView)tabView.findViewById(R.id.tabs_layout_text)).setText(s);
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(s);
            tabSpec.setIndicator(tabView);
            tabSpec.setContent(new FakeContent(getContext()));
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(this);

    }


    private void initViewPager() {
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MusicFragment());
        fragments.add(new AllSongsFragment());
        fragments.add(new ArtistFragment());
        fragments.add(new AlbumsFragment());
        fragments.add(new GenresFragment());
        fragments.add(new PlaylistsFragment());
        fragments.add(new FoldersFragment());
        viewPager.setAdapter(new MyFragmentPageAdapter(getChildFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabHost.setCurrentTab(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String s) {
        int selectedItem = tabHost.getCurrentTab();
        viewPager.setCurrentItem(selectedItem);

        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.h_scrool_view);
        View tabView = tabHost.getCurrentTabView();
        int scrollpos = tabView.getLeft() - (horizontalScrollView.getWidth() - tabView.getWidth()) / 2;
        horizontalScrollView.smoothScrollBy(scrollpos, 0);


    }


    public void directSelect(int id){
        tabHost.setCurrentTab(id);
        viewPager.setCurrentItem(tabHost.getCurrentTab());
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.h_scrool_view);
        View tabView = tabHost.getCurrentTabView();
        int scrollpos = tabView.getLeft() - (horizontalScrollView.getWidth() - tabView.getWidth()) / 2;
        horizontalScrollView.smoothScrollBy(scrollpos, 0);


    }

    @Override
    public void onFragmentInteraction(int id) {
        directSelect(id);
    }


    public class MyFragmentPageAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public MyFragmentPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    public class FakeContent implements TabHost.TabContentFactory {

        private Context context;

        public FakeContent(Context context) {
            this.context = context;
        }

        @Override
        public View createTabContent(String s) {

            View view = new View(context);
            view.setMinimumHeight(0);
            view.setMinimumWidth(0);
            return view;
        }
    }
}
