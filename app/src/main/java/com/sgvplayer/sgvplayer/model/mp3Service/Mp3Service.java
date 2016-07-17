package com.sgvplayer.sgvplayer.model.mp3Service;

import android.media.MediaPlayer;
import android.net.Uri;

import com.sgvplayer.sgvplayer.model.fileNavigator.Mp3File;

import java.util.List;

/**
 * Created by apt_a on 17/07/2016.
 */
public interface Mp3Service {

    void playSong(List<Mp3File> songs, int index);

    void startStop();

    void seek(int ms);

    int getCurrentPosition();

    int getDuration();

    boolean isPlaying();

    void setCurrentPosition(int position);

    void nextSong();

    void previousSong();

    Mp3File getSong();

    int getIndex();
}
