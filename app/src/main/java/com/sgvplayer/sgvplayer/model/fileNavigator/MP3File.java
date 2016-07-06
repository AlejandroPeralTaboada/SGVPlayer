package com.sgvplayer.sgvplayer.model.fileNavigator;

import android.os.Environment;

import java.io.File;

/**
 * Encapsulates a mp3file and checks if it is on the internal storage or on a external sd
 * Created by apt_a on 07/07/2016.
 */
public class MP3File {
    private File file;
    private boolean internalStorage;

    public MP3File (String path){
        file = new File(path);
        internalStorage = isInternalStorage(file);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isInternalStorage() {
        return internalStorage;
    }

    public void setInternalStorage(boolean internalStorage) {
        this.internalStorage = internalStorage;
    }

    private boolean isInternalStorage(File file){
        return file.getAbsolutePath().startsWith(Environment.getExternalStorageDirectory().getAbsolutePath());
    }

}
