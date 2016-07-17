package com.sgvplayer.sgvplayer.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sgvplayer.sgvplayer.R;
import com.sgvplayer.sgvplayer.model.fileNavigator.Mp3File;
import com.sgvplayer.sgvplayer.model.mp3Service.Mp3ServiceImp;

public class PlayerActivity extends AppCompatActivity {

    private Mp3File mp3File;
    private Mp3ServiceImp mp3Service; //need to add to bundle--MAYBE NOT

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //Media Player methods:
    //Called by Mp3ServiceProvider


}
