package com.sgvplayer.sgvplayer.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.sgvplayer.sgvplayer.model.mp3Service.Mp3Service;
import com.sgvplayer.sgvplayer.Mp3ServiceSingleton;

/**
 * Created by Alvaro on 01/08/2016.
 */
public class NotificationReturnSlot extends Activity {

    Mp3Service mp3Service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String action = (String) getIntent().getExtras().get("DO");
        if (action != null) {
            Mp3ServiceSingleton mp3ServiceSingleton = Mp3ServiceSingleton.getInstance();
            mp3Service = mp3ServiceSingleton.getService();
            if (action.equals("startStop")) {
                Log.i("NotificationReturnSlot", "startStop");
                mp3Service.startStop();
            } else if (action.equals("forward")) {
                Log.i("NotificationReturnSlot", "forward");
                mp3Service.nextSong();
            } else if (action.equals("rewind")) {
                Log.i("NotificationReturnSlot", "rewind");
                mp3Service.previousSong();
            }
            finish();
        }
    }
}
