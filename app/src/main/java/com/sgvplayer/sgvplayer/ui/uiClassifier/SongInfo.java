package com.sgvplayer.sgvplayer.ui.uiClassifier;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sgvplayer.sgvplayer.R;
import com.sgvplayer.sgvplayer.model.fileNavigator.FileNavigator;
import com.sgvplayer.sgvplayer.model.fileNavigator.FileNavigatorImp;
import com.sgvplayer.sgvplayer.model.fileNavigator.Mp3File;


public class SongInfo extends Fragment {
    private View view;
    private TextView title;
    private Mp3File song;


    private static final String Music = "music";

    public SongInfo() {
    }


    public static SongInfo newInstance(int song) {
        SongInfo fragment = new SongInfo();
        Bundle args = new Bundle();
        args.putInt(Music, song);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            int index = getArguments().getInt(Music);
            FileNavigator fileNavigator = FileNavigatorImp.getInstance(getActivity());
            song = fileNavigator.getSong(index);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.classfier_song_info_fragment,container,false);
        initUi();
        return view;
    }

    private void initUi(){
        title = (TextView) view.findViewById(R.id.classfier_title);
        title.setText(song.getTitle());
    }
}
