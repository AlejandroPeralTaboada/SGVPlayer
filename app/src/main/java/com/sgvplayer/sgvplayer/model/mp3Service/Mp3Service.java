package com.sgvplayer.sgvplayer.model.mp3Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.sgvplayer.sgvplayer.model.fileNavigator.MP3File;



public class Mp3Service extends Service{
    private MediaPlayer mediaPlayer;
    private MP3File song;

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


    public void playSong(MP3File song) {
        this.song = song;
        if (mediaPlayer!=null){
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, Uri.parse(song.getFile().getAbsolutePath()));
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


}
