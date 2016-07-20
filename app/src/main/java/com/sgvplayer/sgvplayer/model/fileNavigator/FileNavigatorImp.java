package com.sgvplayer.sgvplayer.model.fileNavigator;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Metadata retriever:


/**
 * Implementation of FileNavigator using cursors and media providers
 * Created by apt_a on 07/07/2016.
 */
public class FileNavigatorImp implements FileNavigator {

    private List<Mp3File> files;
    private static FileNavigatorImp instance;
    private Activity activity;

    private FileNavigatorImp(Activity activity) {
        files = findFiles(activity);
    }

    public static FileNavigatorImp getInstance(Activity activity) {
        if (instance == null)
            instance = new FileNavigatorImp(activity);
        return instance;
    }

    private List<Mp3File> findFiles(Activity activity) {
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
    public List<Mp3File> getAllMp3Files() {
        return files;
    }

    @Override
    public List<String> getAllArtists(Activity activity) {
        Set<String> artists= new HashSet<>();
        List<Mp3File> allMp3Files = getAllMp3Files();
        for (Mp3File file : allMp3Files) {
            if (!(artists.contains(file.getArtist()))) {
                artists.add(file.getArtist());
            }
        }
        return new ArrayList<>(artists);
    }

    @Override
    public List<Mp3File> getAllSongsFromArtist(Activity activity, String artist) {
        List<Mp3File> songList = new ArrayList<>();
        List<Mp3File> allMp3Files = getAllMp3Files();
        for (Mp3File file : allMp3Files) {
            if (file.getArtist() != null && file.getArtist().equals(artist)) {
                songList.add(file);
            }
        }
        return songList;
    }

    @Override
    public List<String> getAllAlbums(Activity activity) {
        Set<String> albums= new HashSet<>();
        List<Mp3File> allMp3Files = getAllMp3Files();
        for (Mp3File file : allMp3Files) {
            if (!(albums.contains(file.getAlbum()))) {
                albums.add(file.getAlbum());
            }
        }
        return new ArrayList<>(albums);
    }

    @Override
    public List<String> getAllGenres(Activity activity) {
        List<String> genreList = new ArrayList<>();
        List<Mp3File> allMp3Files = getAllMp3Files();
        for (Mp3File file : allMp3Files) {
            if (!(genreList.contains(file.getGenre()))){
                genreList.add(file.getGenre());
            }
        }
        return genreList;
    }

}
