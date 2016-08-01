package com.sgvplayer.sgvplayer.ui;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.sgvplayer.sgvplayer.Mp3ServiceSingleton;
import com.sgvplayer.sgvplayer.R;
import com.sgvplayer.sgvplayer.model.mp3Service.Mp3Service;

/**
 * Helper class for showing and canceling player
 * notifications.
 * <p/>
 * This class makes heavy use of the {@link NotificationCompat.Builder} helper
 * class to create notifications in a backward-compatible way.
 */
public class PlayerNotification {
    /**
     * The unique identifier for this type of notification.
     */
    private static final String NOTIFICATION_TAG = "Player";

    private Context parent;
    private NotificationManager nManager;
    private NotificationCompat.Builder nBuilder;
    private RemoteViews notificationView;

    //For the UI:
    Mp3Service mp3Service;

    public PlayerNotification(Context parent) {
        this.parent = parent;
        nBuilder = new NotificationCompat.Builder(parent)
                .setContentTitle("SONG TITLE")
                .setContentText("")
                .setSmallIcon(R.drawable.ic_play_arrow_white_24dp);

        notificationView = new RemoteViews(parent.getPackageName(), R.layout.player_notification_custom_layout);

        //Progamatically display Mp3Service info:
        Mp3ServiceSingleton mp3ServiceSingleton = Mp3ServiceSingleton.getInstance();
        mp3Service = mp3ServiceSingleton.getService();
        notificationView.setTextViewText(R.id.music_title, mp3Service.getSong().getTitle());

        //set the button listeners
        setListeners(notificationView);
        nBuilder.setContent(notificationView);

        nManager = (NotificationManager) parent.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(2, nBuilder.build());
    }

    private void setListeners(RemoteViews view) {
        //listener 1
        Intent playPauseIntent = new Intent("com.sgvplayer.sgvplayer.ACTION_PLAYPAUSE");
        PendingIntent playPausePendingIntent = PendingIntent.getBroadcast(parent, 0, playPauseIntent, 0);
        view.setOnClickPendingIntent(R.id.play_pause_button, playPausePendingIntent);

        //listener 2
        Intent forwardIntent = new Intent("com.sgvplayer.sgvplayer.ACTION_FORWARD");
        PendingIntent forwardPendingIntent = PendingIntent.getBroadcast(parent, 1, forwardIntent, 0);
        view.setOnClickPendingIntent(R.id.forward_button, forwardPendingIntent);

        //listener 3
        Intent rewindIntent = new Intent("com.sgvplayer.sgvplayer.ACTION_REWIND");
        PendingIntent rewindPendingIntent = PendingIntent.getBroadcast(parent, 2, rewindIntent, 0);
        view.setOnClickPendingIntent(R.id.rewind_button, rewindPendingIntent);
    }

}
