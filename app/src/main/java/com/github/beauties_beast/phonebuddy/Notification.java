package com.github.beauties_beast.phonebuddy;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by boggs on 10/8/15.
 */
public class Notification {
    private String packageName;
    private String tickerText;
    private String title;
    private String text;

    public Notification(String packageName, String tickerText, String title, String text) {
        this.packageName = packageName;
        this.tickerText = tickerText;
        this.title = title;
        this.text = text;
    }

    public String getAppName(Context context) {
        String appName = "";

        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo;
        try
        {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            appName = (String) packageManager.getApplicationLabel(applicationInfo);
        }
        catch (final PackageManager.NameNotFoundException e)
        {
            appName = packageName;
        }

        return appName;
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
