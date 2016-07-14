package com.sgvplayer.sgvplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sgvplayer.sgvplayer.model.fileNavigator.Mp3File;
import com.sgvplayer.sgvplayer.model.mp3Service.Mp3Service;
import com.sgvplayer.sgvplayer.model.mp3Service.Mp3ServiceProvided;

public class PlayerActivity extends AppCompatActivity
        implements Mp3ServiceProvided {

    private Mp3File mp3File;
    private Mp3Service mp3Service; //need to add to bundle--MAYBE NOT

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //Media Player methods:
    //Called by Mp3ServiceProvider
    @Override
    public void onServiceConnected(Mp3Service mp3Service){
        //implement onServiceConnected
        this.mp3Service = mp3Service;
        this.mp3Service.playSong(mp3File);
    }

}
