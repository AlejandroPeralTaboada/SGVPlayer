package com.sgvplayer.sgvplayer.ui.uiMusicTabs;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;


import com.sgvplayer.sgvplayer.R;
import com.sgvplayer.sgvplayer.model.fileNavigator.Mp3File;
import com.sgvplayer.sgvplayer.model.mp3Service.Mp3Service;

import java.util.ArrayList;
import java.util.List;

public class MusicTabHostFragment extends Fragment
        implements ViewPager.OnPageChangeListener,
        TabHost.OnTabChangeListener,
        MusicFragment.OnFragmentInteractionListener,
        View.OnClickListener {

    ViewPager viewPager;
    TabHost tabHost;
    View view;

    public static int ALL_SONGS = 1;
    public static int ARTIST = 2;
    public static int ALBUM = 3;
    public static int GENRES = 4;
    public static int PLAYLISTS = 5;
    public static int FOLDERS = 6;

    //For the Media Player:
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MP3FILE = "mp3File";
    private Mp3File mp3File;
    private Mp3Service mp3Service;
    private MusicFragment.OnFragmentInteractionListener mListener;
    Thread updateSeekBar;
    SeekBar seekBar;

    public MusicTabHostFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_music_tab_host, container, false);

        initTabHost();
        initViewPager();
     //   initMusicPlayer(); //Necesito mantener el servicio iniciado en el player, no iniciar uno nuevo :(
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Mp3Service) {
            mp3Service =  (Mp3Service) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement mp3Service");
        }
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
        fragments.add(new ArtistsFragment());
        fragments.add(new AlbumsFragment());
        fragments.add(new GenresFragment());
        fragments.add(new PlaylistsFragment());
        fragments.add(new FoldersFragment());
        viewPager.setAdapter(new MyFragmentPageAdapter(getChildFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(this);
    }

    private void initMusicPlayer(){
        ImageButton playPauseButton = (ImageButton) view.findViewById(R.id.play_pause_button);
        playPauseButton.setOnClickListener(this);
        seekBar = (SeekBar) view.findViewById(R.id.seek_bar);
        String songTitle = this.mp3File.getFile().getName();
        TextView scrollingSongTitle = (TextView) view.findViewById(R.id.scrolling_song_title);
        scrollingSongTitle.setText(songTitle);
        scrollingSongTitle.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_pause_button:
                this.mp3Service.startStop();
                break;
        }
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

    //Media Player methods:
    //Called by Mp3ServiceProvider

    //Seek bar:
    private void initSeekBar() {
        updateSeekBar = new Thread() {
            @Override
            public void run() {
                int totalDuration = mp3Service.getDuration();
                int currentPosition = mp3Service.getCurrentPosition();
                seekBar.setMax(totalDuration);
                while (currentPosition < totalDuration) {
                    try {
                        sleep(250);
                        currentPosition = mp3Service.getCurrentPosition();
                        if (!seekBar.isPressed())
                            seekBar.setProgress(currentPosition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        updateSeekBar.start();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp3Service.setCurrentPosition(seekBar.getProgress());
            }
        });
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
