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

import com.sgvplayer.sgvplayer.R;
import com.sgvplayer.sgvplayer.ui.uiMusicTabs.NotificationReturnSlot;

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

    public PlayerNotification (Context parent){
        this.parent = parent;
        nBuilder = new NotificationCompat.Builder(parent)
                .setContentTitle("SONG TITLE")
                .setContentText("")
                .setSmallIcon(R.drawable.ic_play_arrow_white_24dp);

        notificationView = new RemoteViews(parent.getPackageName(),R.layout.player_notification_custom_layout);

        //set the button listeners
        setListeners(notificationView);
        nBuilder.setContent(notificationView);

        nManager = (NotificationManager) parent.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(2, nBuilder.build());
    }

    private void setListeners(RemoteViews view){
        //listener 1
        Intent playPauseIntent = new Intent(parent, NotificationReturnSlot.class);
        playPauseIntent.putExtra("DO","startStop");
        PendingIntent playPausePendingIntent = PendingIntent.getActivity(parent, 0, playPauseIntent, 0);
        view.setOnClickPendingIntent(R.id.play_pause_button, playPausePendingIntent);

        //listener 2
        Intent forwardIntent = new Intent(parent, NotificationReturnSlot.class);
        forwardIntent.putExtra("DO", "forward");
        PendingIntent forwardPendingIntent = PendingIntent.getActivity(parent, 1, forwardIntent, 0);
        view.setOnClickPendingIntent(R.id.forward_button, forwardPendingIntent);

        //listener 3
        Intent rewindIntent = new Intent (parent, NotificationReturnSlot.class);
        rewindIntent.putExtra("DO", "rewind");
        PendingIntent rewindPendingIntent = PendingIntent.getActivity(parent, 2, rewindIntent, 0);
        view.setOnClickPendingIntent(R.id.rewind_button, rewindPendingIntent);

    }

    /**
     * Shows the notification, or updates a previously shown notification of
     * this type, with the given parameters.
     * <p/>
     * TODO: Customize this method's arguments to present relevant content in
     * the notification.
     * <p/>
     * TODO: Customize the contents of this method to tweak the behavior and
     * presentation of player notifications. Make
     * sure to follow the
     * <a href="https://developer.android.com/design/patterns/notifications.html">
     * Notification design guidelines</a> when doing so.
     *
     * @see #cancel(Context)
     */
    public static void notify(final Context context,
                              final String exampleString, final int number) {
        final Resources res = context.getResources();

        // This image is used as the notification's large icon (thumbnail).
        // TODO: Remove this if your notification has no relevant thumbnail.
        final Bitmap picture = BitmapFactory.decodeResource(res, R.drawable.example_picture);


        final String ticker = exampleString;
        final String title = res.getString(
                R.string.player_notification_title_template, exampleString);//Get title of playing song
        final String text = res.getString(
                R.string.player_notification_placeholder_text_template, exampleString);//Get author? of playing song

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)

                // Set appropriate defaults for the notification light, sound,
                // and vibration.
                .setDefaults(Notification.DEFAULT_ALL)

                //INSTANTIATE REMOTEVIEWS AND USE CUSTOM LAYOUT:
                //RemoteViews notificationView = new RemoteViews(getPackageName(),R.layout.player_notification_custom_layout);

                // Set required fields, including the small icon, the
                // notification title, and text.
                .setSmallIcon(R.drawable.ic_g_clef)
                .setContentTitle(title)
                .setContentText(text)

                // All fields below this line are optional.

                // Use a default priority (recognized on devices running Android
                // 4.1 or later)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Provide a large icon, shown with the notification in the
                // notification drawer on devices running Android 3.0 or later.
                .setLargeIcon(picture)

                // Set ticker text (preview) information for this notification.
                .setTicker(ticker)

                // Show a number. This is useful when stacking notifications of
                // a single type.
                .setNumber(number)

                // If this notification relates to a past or upcoming event, you
                // should set the relevant time information using the setWhen
                // method below. If this call is omitted, the notification's
                // timestamp will by set to the time at which it was shown.
                // TODO: Call setWhen if this notification relates to a past or
                // upcoming event. The sole argument to this method should be
                // the notification timestamp in milliseconds.
                //.setWhen(...)

                // Set the pending intent to be initiated when the user touches
                // the notification.
                .setContentIntent(
                        PendingIntent.getActivity(
                                context,
                                0,
                                new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")),
                                PendingIntent.FLAG_UPDATE_CURRENT))

                // Automatically dismiss the notification when it is touched.
                .setAutoCancel(true);

        notify(context, builder.build());
    }

    public void notificationCancel() {
        nManager.cancel(2);
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG, 0, notification);
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(), notification);
        }
    }

    /**
     * Cancels any notifications of this type previously shown using
     * {@link #notify(Context, String, int)}.
     */
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static void cancel(final Context context) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0);
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode());
        }
    }
}
