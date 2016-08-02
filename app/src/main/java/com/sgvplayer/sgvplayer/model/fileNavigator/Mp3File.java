package com.sgvplayer.sgvplayer.model.fileNavigator;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sgvplayer.sgvplayer.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

/**
 * Encapsulates a mp3file and checks if it is on the internal storage or on a external sd
 * Created by apt_a on 07/07/2016.
 */
public class Mp3File {
    private File file;
    private boolean internalStorage;
    private MediaMetadataRetriever metadataRetriever;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public Mp3File(String path){
        file = new File(path);
        internalStorage = isInternalStorage(file);
        setDataSource();
    }

    public Mp3File(String path,int id){
        file = new File(path);
        internalStorage = isInternalStorage(file);
        setDataSource();
        this.id = id;
    }

    private void setDataSource(){
        metadataRetriever = new MediaMetadataRetriever();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file.getAbsolutePath());
            metadataRetriever.setDataSource(inputStream.getFD());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

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

    public String getTitle(){
        String title = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        if ((title==null) || title.trim().equals(""))
            title = file.getName();
        return title;
    }

    //Metadata retrieving:

    public String getArtist(){
        return metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
    }

    public String getAlbum(){
        return metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
    }

    public String getGenre(){
        return metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
    }

    public Bitmap getAlbumCover(){
        byte [] imageData = metadataRetriever.getEmbeddedPicture();
        if (imageData != null){
            return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        }
        return null; //Better return a default icon...

    }

}
