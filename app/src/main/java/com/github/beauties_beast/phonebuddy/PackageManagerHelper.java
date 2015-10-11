package com.github.beauties_beast.phonebuddy;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public ArrayList<String> getAllPackageNames() {
        ArrayList<String> packageNames = new ArrayList<>();
        for(ApplicationInfo applicationInfo : getAllPackages()) {
            packageNames.add(applicationInfo.packageName);
        }
        Collections.sort(packageNames.subList(0, packageNames.size()));
        return packageNames;
    }

    public ArrayList<String> getAllAppNamesAndPackageNames() {
        ArrayList<String> packageNames = new ArrayList<>();
        for(ApplicationInfo applicationInfo : getAllPackages()) {
            packageNames.add(String.format("%s (%s)", getAppName(applicationInfo.packageName), applicationInfo.packageName));
        }
        Collections.sort(packageNames.subList(0, packageNames.size()));
        return packageNames;
    }

    public String getPackageNameFromParentheses(String s) {
        String packageName = "default";
        String regex = "(.*?)(\\()(.*?)(\\))(.*?)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        if(matcher.matches()) {
            packageName = matcher.group(3);
        }
        return packageName;
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
