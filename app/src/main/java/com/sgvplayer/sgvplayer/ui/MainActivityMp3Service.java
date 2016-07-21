package com.sgvplayer.sgvplayer.ui;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;

import com.sgvplayer.sgvplayer.model.fileNavigator.Mp3File;
import com.sgvplayer.sgvplayer.model.mp3Service.Mp3Service;

import java.util.List;

/**
 * Created by Alvaro and Alejandro Peral on 21/07/2016.
 */
public abstract class MainActivityMp3Service extends AppCompatActivity implements Mp3Service {

    Mp3Service mp3Service;

    @Override
    public void playSong(List<Mp3File> songs, int index) {
        mp3Service.playSong(songs,index);
    }

    @Override
    public void startStop() {
        mp3Service.startStop();
    }

    @Override
    public void seek(int ms) {
        mp3Service.seek(ms);
    }

    @Override
    public int getCurrentPosition() {
        return mp3Service.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mp3Service.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return mp3Service.isPlaying();
    }

    @Override
    public void setCurrentPosition(int position) {
        mp3Service.setCurrentPosition(position);
    }

    @Override
    public void nextSong() {
        mp3Service.nextSong();
    }

    @Override
    public void previousSong() { mp3Service.previousSong(); }

    @Override
    public Mp3File getSong() {
        return mp3Service.getSong();
    }

    @Override
    public int getIndex() { return mp3Service.getIndex(); }

    @Override
    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener){
        mp3Service.setOnCompletionListener(onCompletionListener);
    }

    @Override
    public void setOnPreparedListener(MediaPlayer.OnPreparedListener onPreparedListener){
        mp3Service.setOnPreparedListener(onPreparedListener);
    }

}
