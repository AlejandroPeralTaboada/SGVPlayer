package com.sgvplayer.sgvplayer.model.mp3Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.sgvplayer.sgvplayer.R;
import com.sgvplayer.sgvplayer.model.fileNavigator.MP3File;


public class Mp3Service extends Service {
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
        Toast.makeText(this, R.string.ServiceStoped, Toast.LENGTH_LONG).show();
        super.onDestroy();
        mediaPlayer.stop();
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

    public boolean isPlayin() {

        return mediaPlayer.isPlaying();
    }

    public void setCurrentPosition(int position) {
        mediaPlayer.seekTo(position);
    }

    public void setSong(MP3File song) {
        this.song = song;
        mediaPlayer = MediaPlayer.create(this, Uri.parse(song.getFile().getAbsolutePath()));
        mediaPlayer.start();
    }

    public class LocalService extends Binder {
        Mp3Service getService() {
            return Mp3Service.this;
        }
    }


}
