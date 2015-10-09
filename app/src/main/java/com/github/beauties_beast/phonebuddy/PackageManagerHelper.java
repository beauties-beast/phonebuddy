package com.github.beauties_beast.phonebuddy;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boggs on 10/9/15.
 */
public class PackageManagerHelper {
    private Context context;
    private final PackageManager packageManager;

    public PackageManagerHelper(Context context) {
        this.context = context;
        packageManager = context.getPackageManager();
    }

    public List<ApplicationInfo> getAllPackages() {
        return packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
    }

    public String getAppName(String packageName) {
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
}
