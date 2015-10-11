package com.github.beauties_beast.phonebuddy;

import android.content.Context;
import android.util.Log;

/**
 * Created by boggs on 10/9/15.
 */
public class ServiceManager {
    private static final String TAG = "ServiceManager";

    private static ServiceManager instance = new ServiceManager();

    public static ServiceManager getInstance() {
        return instance;
    }

    private ServiceManager() {
        active = false;
    }

    private boolean active;
    private Context context;
    private DatabaseHelper databaseHelper;

    public void initContext(Context context) {
        this.context = context;
    }

    public void initDatabaseHelper() {
        databaseHelper = new DatabaseHelper(context);
    }

    public boolean isActive() {
        Log.d(TAG, String.format("Service active: %s", String.valueOf(active)));
        return active;
    }

    public boolean setActive(boolean active) {
        if (active) {
            if (databaseHelper.getBuddyPhones().size() > 0) {
                this.active = true;
                databaseHelper.resetNotifications();
                return true;
            } else {
                this.active = false;
                return false;
            }
        } else {
            this.active = false;
            return true;
        }
    }

    public boolean refreshStatus() {
        return setActive(active);
    }
}
