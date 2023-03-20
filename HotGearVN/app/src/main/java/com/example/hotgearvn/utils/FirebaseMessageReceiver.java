package com.example.hotgearvn.utils;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.hotgearvn.R;
import com.example.hotgearvn.activity.InvoiceDetailActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessageReceiver extends FirebaseMessagingService {
    // Override onNewToken to get new token
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("Token", "Refreshed token: " + token);
    }

    // Override onMessageReceived() method to extract the
    // title and
    // body from the message passed in FCM
    @Override
    public void
    onMessageReceived(RemoteMessage remoteMessage) {
        // First case when notifications are received via
        // data event
        // Here, 'title' and 'message' are the assumed names
        // of JSON
        // attributes. Since here we do not have any data
        // payload, This section is commented out. It is
        // here only for reference purposes.
        if (remoteMessage.getData().size() > 0) {
            Log.d("title", remoteMessage.getData().get("title"));
            Log.d("message", remoteMessage.getData().get("title"));
            Log.d("invoiceId", remoteMessage.getData().get("title"));
            showNotification(remoteMessage.getData().get("title"),
                    remoteMessage.getData().get("message"), remoteMessage.getData().get("invoiceId"));
        }

//        // Second case when notification payload is
//        // received.
//        if (remoteMessage.getNotification() != null) {
//            // Since the notification is received directly
//            // from FCM, the title and the body can be
//            // fetched directly as below.
//            showNotification(
//                    remoteMessage.getNotification().getTitle(),
//                    remoteMessage.getNotification().getBody(),remoteMessage.getData().get("invoiceId"));
//        }
    }

    private RemoteViews getCustomDesign(String title, String message) {
       RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.title, title);
        remoteViews.setTextViewText(R.id.message, message);
        remoteViews.setImageViewResource(R.id.imgNotification,
                R.drawable.google);
        return remoteViews;
    }

    // Method to display the notifications
    public void showNotification(String title,
                                 String message, String invoiceId) {
        // Pass the intent to switch to the MainActivity
        Intent intent
                = new Intent(this, InvoiceDetailActivity.class);
        intent.putExtra("invoiceId", Long.valueOf(invoiceId));
        // Assign channel ID
        String channel_id = "notification_channel";
        // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
        // the activities present in the activity stack,
        // on the top of the Activity that is to be launched
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Pass the intent to PendingIntent to start the
        // next Activity
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent
                = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        // Create a Builder object using NotificationCompat
        // class. This will allow control over all the flags
        NotificationCompat.Builder builder
                = new NotificationCompat
                .Builder(getApplicationContext(),
                channel_id)
                .setSmallIcon(R.drawable.google)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000,
                        1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setContent(getCustomDesign(title, message));
        // Create an object of NotificationManager class to
        // notify the
        // user of events that happen in the background.
        NotificationManager notificationManager
                = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel
                    = new NotificationChannel(
                    channel_id, "web_app",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(
                    notificationChannel);
        }

        notificationManager.notify(0, builder.build());
    }
}
