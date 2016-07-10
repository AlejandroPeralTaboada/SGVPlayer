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

    public Mp3ServiceProvider(Mp3ServiceProvided mp3ServiceProvided, Activity activity){
        this.mp3ServiceProvided = mp3ServiceProvided;
        this.activity=activity;
        Intent intent = new Intent(activity.getBaseContext(), Mp3Service.class);

        activity.bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    public void disconnect(){
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
