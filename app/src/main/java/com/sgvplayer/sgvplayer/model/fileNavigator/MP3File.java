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
import java.io.Serializable;

/**
 * Encapsulates a mp3file and checks if it is on the internal storage or on a external sd
 * Created by apt_a on 07/07/2016.
 */
public class Mp3File implements Serializable {
    private File file;
    private boolean internalStorage;
    MediaMetadataRetriever metadataRetriever;

    public Mp3File(String path){
        file = new File(path);
        internalStorage = isInternalStorage(file);
        metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(this.file.getPath());
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

    public String getName(){
        return this.file.getName();
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
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
    }

}
