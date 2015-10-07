package com.github.beauties_beast.phonebuddy;

import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by boggs on 9/30/15.
 */
public class SmsHelper {
    private static final String TAG = "SmsHelper";

    public static boolean sendSmsWithHeaders(String phoneNumber, String body) {
        String message = "PhoneBuddy:\n" + body;
        return SmsHelper.sendSms(phoneNumber, message);
    }

    public static boolean sendSms(String phoneNumber, String body) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            Log.d(TAG, String.format("sendSms %s %s", phoneNumber, body));
            smsManager.sendTextMessage(phoneNumber, null, body, null, null);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }
}
