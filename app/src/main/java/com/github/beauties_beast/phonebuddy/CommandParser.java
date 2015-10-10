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
        String keyword = sms.split(" ")[0];
        boolean parsed = false;

        Log.d(TAG, String.format("%s %s", TAG, keyword));

        CommandInterface command;

        switch(keyword) {
            case "!SMS":
                command = new SmsCommand();
                parsed = command.parse(sms, context);
                break;
            case "!SOS":
                break;
            case "!LOCATION":
                break;
            case "!STATUS":
                break;
            default:
                parsed = false;
                break;
        }
        return parsed;
    }
}
