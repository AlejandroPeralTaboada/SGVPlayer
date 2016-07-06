package com.sgvplayer.sgvplayer.model.fileNavigator;

import android.app.Activity;

import java.util.List;

/**
 * Interface to navigate mp3files
 * Created by alejandro on 4/07/16.
 */
public interface FileNavigator {
    /**
     * Returns a list of files that contains all mp3 files of the device.
     * This method requires external data storage permissions
     * @param activity the activity who invokes that method
     * @return a list of MP3Files of all system
     */
    List<MP3File> getAllMp3Files(Activity activity);
}
