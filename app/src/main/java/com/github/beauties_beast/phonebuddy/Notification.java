package com.github.beauties_beast.phonebuddy;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by boggs on 10/8/15.
 */
public class Notification {
    private String packageName;
    private String tickerText;
    private String title;
    private String text;
    private Date createdAt;

    public Notification(String packageName, String tickerText, String title, String text) {
        this.packageName = packageName;
        this.tickerText = tickerText;
        this.title = title;
        this.text = text;
    }

    public Notification(String packageName, String tickerText, String title, String text, Date createdAt) {
        this.packageName = packageName;
        this.tickerText = tickerText;
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getAppName(Context context) {
        PackageManagerHelper packageManagerHelper = new PackageManagerHelper(context);
        return packageManagerHelper.getAppName(packageName);
    }

    public String getPreferredTitle() {
        if(title == null || title.equals("")) {
            return "";
        } else {
            return title;
        }
    }

    public String getPreferredText() {
        if(tickerText == null || tickerText.equals("")) {
            return text;
        } else {
            return tickerText;
        }
    }

    public String getSimpleCreatedAt() {
        SimpleDateFormat createdAtFormat = new SimpleDateFormat("MMM d, yyyy 'at' h:mm a");
        return createdAtFormat.format(createdAt).toString();
    }

    public String formatForSms(Context context) {
        return String.format("Received notification from %s (%s): \"%s\"", getAppName(context), getPackageName(), getPreferredText());
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTickerText() {
        return tickerText;
    }

    public void setTickerText(String tickerText) {
        this.tickerText = tickerText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
