package com.sgvplayer.sgvplayer.model.mp3Service;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;


public class Mp3ServiceProvider implements ServiceConnection {


    private Mp3ServiceProvided mp3ServiceProvided;
    private Activity activity;
    private static Mp3Service mp3Service;

    public Mp3ServiceProvider(Mp3ServiceProvided mp3ServiceProvided, Activity activity) {
        this.mp3ServiceProvided = mp3ServiceProvided;
        this.activity = activity;
        if (mp3Service == null) {
            Intent intent = new Intent(activity.getBaseContext(), Mp3Service.class);
            activity.startService(intent);
            activity.bindService(intent, this, Context.BIND_AUTO_CREATE);
        }else{
            mp3ServiceProvided.onServiceConnected(mp3Service);
        }
    }

    public void disconnect() {
        activity.unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Mp3Service.LocalService localService = (Mp3Service.LocalService) iBinder;
        mp3ServiceProvided.onServiceConnected(localService.getService());
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
}
