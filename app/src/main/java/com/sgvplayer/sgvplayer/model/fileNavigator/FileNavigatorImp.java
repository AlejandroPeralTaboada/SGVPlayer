package com.sgvplayer.sgvplayer.model.fileNavigator;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of FileNavigator using cursors and media providers
 * Created by apt_a on 07/07/2016.
 */
public class FileNavigatorImp implements FileNavigator {

    @Override
    public List<MP3File> getAllMp3Files(Activity activity) {
        Uri uri = MediaStore.Audio.Media.getContentUri("external");
        String[] cols = new String[]{MediaStore.Audio.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = activity.getContentResolver().query(uri, cols, MediaStore.Audio.Media.DATA + " LIKE ? or " + MediaStore.Audio.Media.DATA + " LIKE ?", new String[]{"%.mp3", "%.MP3"}, null);
            if (cursor == null)
                return null;
            int idxData = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            List<MP3File> mp3Files = new ArrayList<>();
            while (cursor.moveToNext()) {
                mp3Files.add(new MP3File(cursor.getString(idxData)));
            }
            return mp3Files;
        } catch (NullPointerException e) {
            Log.e("Cursor error", e.getLocalizedMessage());
            return null;
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }
}
