package com.sgvplayer.sgvplayer.ui.uiClassifier;

import android.media.MediaMetadata;
import android.media.MediaMetadataEditor;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sgvplayer.sgvplayer.R;
import com.sgvplayer.sgvplayer.model.fileNavigator.FileNavigator;
import com.sgvplayer.sgvplayer.model.fileNavigator.FileNavigatorImp;
import com.sgvplayer.sgvplayer.model.fileNavigator.Mp3File;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagConstant;
import org.farng.mp3.TagException;
import org.farng.mp3.TagOptionSingleton;
import org.farng.mp3.id3.AbstractID3v2;
import org.farng.mp3.id3.AbstractID3v2Frame;
import org.farng.mp3.id3.AbstractID3v2FrameBody;
import org.farng.mp3.id3.FrameBodyTALB;
import org.farng.mp3.id3.FrameBodyTIT1;
import org.farng.mp3.id3.FrameBodyTIT2;
import org.farng.mp3.id3.ID3v2_4Frame;

import java.io.IOException;


public class SongInfo extends Fragment {
    private View view;
    private EditText title;
    private EditText artist;
    private Button button;
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
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.classfier_song_info_fragment, container, false);
        initUi();
        return view;
    }


    private void initUi() {
        button = (Button) view.findViewById(R.id.confirmButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMetadata();
            }
        });
        title = (EditText) view.findViewById(R.id.classfier_title);
        title.setText(song.getTitle());
        artist = (EditText) view.findViewById(R.id.classfier_artist);
        artist.setText(song.getArtist());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate();
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void setMetadata() {
        try {
            MP3File mp3File = new MP3File(song.getFile());
            TagOptionSingleton.getInstance().setDefaultSaveMode(TagConstant.MP3_FILE_SAVE_OVERWRITE);
            AbstractID3v2 id3v2 = mp3File.getID3v2Tag();
            AbstractID3v2Frame frame = id3v2.getFrame("TIT2");
            if (frame == null) {
                AbstractID3v2FrameBody frameBody;
                frameBody = new FrameBodyTIT2((byte) 0, title.getText().toString());
                frame = new ID3v2_4Frame(frameBody);
                id3v2.setFrame(frame);
            } else {
                ((FrameBodyTIT2) frame.getBody()).setText(title.getText().toString());
            }
            mp3File.save();
        } catch (TagException | IOException e) {
            e.printStackTrace();
        }
    }
}
