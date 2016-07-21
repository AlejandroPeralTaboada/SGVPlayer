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
import com.sgvplayer.sgvplayer.model.fileNavigator.FileNavigatorImp;
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

    private MusicFragment.OnFragmentInteractionListener mListener;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MP3FILE = "mp3File";

    //For the Media Player:
    private int index;
    private Mp3File mp3File;

    //For the UI:
    Thread updateSeekBar;
    SeekBar seekBar;
    ImageButton playPauseButton;
    TextView scrollingSongTitle;

    private Mp3Service mp3Service;

    public MusicTabHostFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_music_tab_host, container, false);

        initTabHost();
        initViewPager();
        initMusicPlayer();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_pause_button:
                playPauseButtonAction();
                break;
            case R.id.forward_button:
                forwardButtonAction();
                break;
            case R.id.rewind_button:
                rewindButtonAction();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        tabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

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
    private void initMusicPlayer(){
        if (mp3Service.isReady()){
            index = mp3Service.getIndex();
            mp3File = mp3Service.getSong();
            initPlayerUI();
        } else {
            //set listener and wait? Then, as a callback:
            //FileNavigatorImp fileNavigator = FileNavigatorImp.getInstance(getActivity());
            //mp3Service.playSong(fileNavigator.getAllMp3Files(),0);
            //mp3Service.startStop();
        }
    }

    /**
     * Initialises the player widget UI
     */
    private void initPlayerUI(){
        playPauseButton = (ImageButton) view.findViewById(R.id.play_pause_button);
        playPauseButton.setOnClickListener(this);

        ImageButton forwardButton = (ImageButton) view.findViewById(R.id.forward_button);
        forwardButton.setOnClickListener(this);

        ImageButton rewindButton = (ImageButton) view.findViewById(R.id.rewind_button);
        rewindButton.setOnClickListener(this);

        seekBar = (SeekBar) view.findViewById(R.id.seek_bar);
        initSeekBar();

        String songTitle = this.mp3File.getFile().getName();
        scrollingSongTitle = (TextView) view.findViewById(R.id.scrolling_song_title);
        scrollingSongTitle.setText(songTitle);
        scrollingSongTitle.setSelected(true);
    }

    //Seek bar:
    private void initSeekBar() {
        updateSeekBar = new Thread() {
            @Override
            public void run() {
                int totalDuration = mp3Service.getDuration();
                int currentPosition = mp3Service.getCurrentPosition();
                int adv = totalDuration - currentPosition;
                adv = (adv<500) ? adv : 500 ;
                seekBar.setMax(totalDuration);
                Thread thisThread = Thread.currentThread();
                while ((adv>2) && updateSeekBar == thisThread) {
                    try {
                        if (mp3Service.isReady()){
                            currentPosition = mp3Service.getCurrentPosition();
                            if (!seekBar.isPressed())
                                seekBar.setProgress(currentPosition);
                            sleep(adv);
                            adv = totalDuration - currentPosition;
                            adv = (adv<500) ? adv : 500 ;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        seekBar.setProgress(totalDuration);
                        break;
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

    private void stopSeekBar(){
        this.updateSeekBar = null;
    }

    private void playPauseButtonAction(){
        this.mp3Service.startStop();
        if (this.mp3Service.isPlaying()){
            playPauseButton.setImageResource(R.drawable.ic_pause_black_24dp);
        } else {
            playPauseButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        }
    }

    private void forwardButtonAction(){
        mp3Service.nextSong();
        index = mp3Service.getIndex();
        mp3File = mp3Service.getSong();
        stopSeekBar();
        updatePlayerUI();
    }

    private void rewindButtonAction(){
        mp3Service.previousSong();
        index = mp3Service.getIndex();
        mp3File = mp3Service.getSong();
        stopSeekBar();
        updatePlayerUI();
    }

    private void updatePlayerUI(){
        initSeekBar();
        String songTitle = this.mp3File.getFile().getName();
        scrollingSongTitle.setText(songTitle);
        scrollingSongTitle.setSelected(true);
    }

    //Tab Host Adapter:

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
