package com.github.beauties_beast.phonebuddy;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by boggs on 10/9/15.
 */
public class SmsCommand implements CommandInterface {
    private static final String TAG = "SmsCommand";
    private String regex = "(!SMS)(\\s)(.*?)(/)(.*?)";
    private Pattern pattern;
    private Matcher matcher;

    public SmsCommand() {
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean parse(String sms, Context context) {
        Log.d(TAG, String.format("%s parse", TAG));
        matcher = pattern.matcher(sms);
        HashMap<String, String> options = new HashMap<>();
        if(matcher.matches()) {
            String contact = matcher.group(3).trim();
            String message = matcher.group(5).trim();
            Log.d(TAG, String.format("%s parse match %s %s", TAG, contact, message));
            options.put("contact", contact);
            options.put("message", message);
            return action(options, context);
        } else {
            return false;
        }
    }

    @Override
    public boolean action(Object options, Context context) {
        Log.d(TAG, String.format("%s action", TAG));
        HashMap<String, String> params = (HashMap<String, String>) options;
        String phoneNumber= SmsHelper.getPhoneNumber(params.get("contact"), context);
        SmsHelper.sendSms(phoneNumber, params.get("message"), SmsHelper.WITH_FOOTER);
        return true;
    }

    @Override
    public boolean response(Object options, Context context) {
        return false;
    }

    public static void main(String[] args) {
        SmsCommand s = new SmsCommand();
        s.parse("!SMS Boggs Aldanese / Test", null);
    }
}
