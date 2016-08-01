package com.sgvplayer.sgvplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sgvplayer.sgvplayer.model.mp3Service.Mp3Service;

/**
 * Created by Alvaro on 01/08/2016.
 */
public class PlayerNotificationBroadcastReceiver extends BroadcastReceiver {
    Mp3Service mp3Service;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            Mp3ServiceSingleton mp3ServiceSingleton = Mp3ServiceSingleton.getInstance();
            mp3Service = mp3ServiceSingleton.getService();
            if (action.equalsIgnoreCase("com.sgvplayer.sgvplayer.ACTION_PLAYPAUSE")) {
                mp3Service.startStop();
            } else if (action.equalsIgnoreCase("com.sgvplayer.sgvplayer.ACTION_FORWARD")) {
                mp3Service.nextSong();
            } else if (action.equalsIgnoreCase("com.sgvplayer.sgvplayer.ACTION_REWIND")) {
                mp3Service.previousSong();
            }
        }
    }

}
