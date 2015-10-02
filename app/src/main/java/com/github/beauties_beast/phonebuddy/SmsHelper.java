package com.github.beauties_beast.phonebuddy;

import android.telephony.SmsManager;

/**
 * Created by boggs on 9/30/15.
 */
public class SmsHelper {
    public static boolean sendSms(String phoneNumber, String body) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, body, null, null);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }
}
