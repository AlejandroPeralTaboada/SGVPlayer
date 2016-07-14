package com.sgvplayer.sgvplayer.model.mp3Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.sgvplayer.sgvplayer.model.fileNavigator.Mp3File;

import java.util.List;


public class Mp3Service extends Service{
    private MediaPlayer mediaPlayer;
    private List<Mp3File> songs;
    private int index;

    private final IBinder iBinder = new LocalService();

    public Mp3Service() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    protected class LocalService extends Binder {
        Mp3Service getService() {
            return Mp3Service.this;
        }
    }


    public void playSong(List<Mp3File> songs,int index) {
        this.songs = songs;
        this.index = index;
        if (mediaPlayer!=null){
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, Uri.parse(songs.get(index).getFile().getAbsolutePath()));
        mediaPlayer.start();
    }

    public void startStop() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

    public void seek(int ms) {

        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + ms);
    }

    public int getCurrentPosition() {

        return mediaPlayer.getCurrentPosition();
    }

    public int getDuration() {

        return mediaPlayer.getDuration();
    }

    public boolean isPlaying() {

        return mediaPlayer.isPlaying();
    }

    public void setCurrentPosition(int position) {

        mediaPlayer.seekTo(position);
    }

    public void nextSong(){
        mediaPlayer.release();
        index = (index+1)%songs.size();
        mediaPlayer = MediaPlayer.create(this, Uri.parse(songs.get(index).getFile().getAbsolutePath()));
        mediaPlayer.start();
    }

    public void previousSong(){
        mediaPlayer.release();
        index = (index-1)%songs.size();
        if (index <0)
            index = songs.size()-1;
        mediaPlayer = MediaPlayer.create(this, Uri.parse(songs.get(index).getFile().getAbsolutePath()));
        mediaPlayer.start();
    }


}
