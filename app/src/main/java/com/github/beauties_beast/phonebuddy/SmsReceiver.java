package com.github.beauties_beast.phonebuddy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by boggs on 9/30/15.
 */
public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for(int i = 0; i < smsMessages.length; i++) {
            String address = smsMessages[i].getOriginatingAddress();
            String body = smsMessages[i].getMessageBody();
            Log.d(TAG, "address: " + address);
            Log.d(TAG, "body: " + body);
            //TODO: Send message body and originating address to class that parses commands.
        }
    }
}
