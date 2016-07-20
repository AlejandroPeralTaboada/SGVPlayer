package com.sgvplayer.sgvplayer.model.fileNavigator;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//Metadata retriever:


/**
 * Implementation of FileNavigator using cursors and media providers
 * Created by apt_a on 07/07/2016.
 */
public class FileNavigatorImp implements FileNavigator {

    @Override
    public List<Mp3File> getAllMp3Files(Activity activity) {
        Uri uri = MediaStore.Audio.Media.getContentUri("external");
        String[] cols = new String[]{MediaStore.Audio.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = activity.getContentResolver().query(uri, cols, MediaStore.Audio.Media.DATA + " LIKE ? or " + MediaStore.Audio.Media.DATA + " LIKE ?", new String[]{"%.mp3", "%.MP3"}, null);
            if (cursor == null)
                return null;
            int idxData = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            List<Mp3File> mp3Files = new ArrayList<>();
            while (cursor.moveToNext()) {
                mp3Files.add(new Mp3File(cursor.getString(idxData)));
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

    @Override
    public List<String> getAllArtists(Activity activity){
        Uri uri = MediaStore.Audio.Media.getContentUri("external");
        String[] cols = new String[]{"DISTINCT " +MediaStore.Audio.Media.ARTIST};
        Cursor cursor = null;
        try {
            cursor = activity.getContentResolver().query(uri, cols, MediaStore.Audio.Media.ARTIST,null, null);
            if (cursor == null)
                return null;
            int idxData = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
            List<String> mp3Files = new ArrayList<>();
            while (cursor.moveToNext()) {
                mp3Files.add(cursor.getString(idxData));
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
    
    @Override
    public List<Mp3File> getAllSongsFromArtist(Activity activity, String artist){
        List<Mp3File> songList = new ArrayList<>();
        List<Mp3File> allMp3Files = getAllMp3Files(activity);
        for (Mp3File file : allMp3Files){
            if (file.getArtist() != null && file.getArtist().equals(artist)){
                songList.add(file);
            }
        }
        return songList;
    }

    @Override
    public List<String> getAllAlbums(Activity activity){
        List<String> albumList = new ArrayList<>();
        List<Mp3File> allMp3Files = getAllMp3Files(activity);
        for (Mp3File file : allMp3Files){
            if (!(albumList.contains(file.getAlbum()))){
                albumList.add(file.getAlbum());
            }
        }
        return albumList;
    }

    @Override
    public List<String> getAllGenres(Activity activity){
        List<String> genreList = new ArrayList<>();
        List<Mp3File> allMp3Files = getAllMp3Files(activity);
        for (Mp3File file : allMp3Files){
            if (!(genreList.contains(file.getGenre()))){
                genreList.add(file.getGenre());
            }
        }
        return genreList;
    }

}
