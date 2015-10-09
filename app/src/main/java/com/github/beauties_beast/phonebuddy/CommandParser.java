package com.github.beauties_beast.phonebuddy;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by boggs on 10/9/15.
 */
public class CommandParser {
    private static final String TAG = "CommandParser";
    private Context context;

    public CommandParser(Context context) {
        this.context = context;
    }

    public boolean parse(String sms) {
        String[] smsArr = sms.split(" ");
        String keyword = smsArr[0];
        boolean parsed = false;

        Log.d(TAG, String.format("%s %s", TAG, keyword));

        switch(keyword) {
            case "SMS":
                break;
            case "SOS":
                break;
            case "LOCATION":
                break;
            case "STATUS":
                break;
            default:
                parsed = false;
                break;
        }
        return parsed;
    }
}
