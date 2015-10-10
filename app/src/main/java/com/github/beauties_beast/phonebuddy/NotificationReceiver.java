package com.github.beauties_beast.phonebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by boggs on 9/30/15.
 */
public class NotificationReceiver extends NotificationListenerService {
    private static final String TAG = "NotificationReceiver";

    DatabaseHelper databaseHelper;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if(ServiceManager.getInstance().isActive()) {
            String packageName = sbn.getPackageName();
            String tickerText = "";
            if (sbn.getNotification().tickerText != null)
                tickerText = sbn.getNotification().tickerText.toString();

            Bundle extras = sbn.getNotification().extras;
            String title = "";
            String text = "";

            if (extras.getCharSequence("android.title") != null)
                try {
                    title = (extras.getString("android.title") + "").toString();
                } catch (ClassCastException e) {
                    title = "";
                }
            if (extras.getCharSequence("android.text") != null)
                try {
                    text = (extras.getCharSequence("android.text") + "").toString();
                } catch (ClassCastException e) {
                    text = "";
                }

            Notification notification = new Notification(packageName, tickerText, title, text);

            Log.d(TAG, "packageName: " + packageName);
            Log.d(TAG, "tickerText: " + tickerText);
            Log.d(TAG, "title: " + title);
            Log.d(TAG, "text: " + text);

            //TODO: Send app name, packageName, and tickerText (or full text) to class that forwards messages to buddy phone.

            databaseHelper = new DatabaseHelper(getBaseContext());
            databaseHelper.addNotification(notification);

            super.onNotificationPosted(sbn);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }
}
