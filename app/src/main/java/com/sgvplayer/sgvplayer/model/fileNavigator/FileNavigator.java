package com.sgvplayer.sgvplayer.model.fileNavigator;

import android.app.Activity;

import java.util.List;

/**
 * Interface to navigate (@link Mp3File)s
 * Created by Alejandro Peral and Alvaro Furlan Falcao on 4/07/16.
 */
public interface FileNavigator {
    /**
     * Returns a list of files that contains all mp3 files of the device.
     * This method requires external data storage permissions
     * @return a list of MP3Files of all system
     */
    List<Mp3File> getAllMp3Files();

    List<String> getAllArtists(Activity activity);

    List <Mp3File> getAllSongsFromArtist(Activity activity, String artist);

    List<String> getAllAlbums (Activity activity);

    List<Mp3File> getAllSongsFromAlbum(String album);

    List<String> getAllGenres (Activity activity);

    List<Mp3File> getAllSongsFromGenre(String genre);
}
