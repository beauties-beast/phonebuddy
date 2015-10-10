package com.github.beauties_beast.phonebuddy;

import android.content.Context;

/**
 * Created by boggs on 10/10/15.
 */
public class NotificationForwarder implements ForwarderInterface {
    private static final String TAG = "NotificationForwarder";

    @Override
    public boolean parse(Object object, Context context) {
        Notification notification = (Notification) object;
        String body = notification.formatForSms(context);
        return action(body, context);
    }

    @Override
    public boolean action(Object options, Context context) {
        String body = (String) options;
        BuddyPhonesHelper buddyPhonesHelper = new BuddyPhonesHelper(context);
        buddyPhonesHelper.sendSmsToAllBuddies(body);
        return false;
    }

    @Override
    public boolean response(Object options, Context context) {
        return false;
    }
}
