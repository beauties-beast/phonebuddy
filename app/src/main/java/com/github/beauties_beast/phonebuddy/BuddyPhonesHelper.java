package com.github.beauties_beast.phonebuddy;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by boggs on 10/10/15.
 */
public class BuddyPhonesHelper {
    private static final String TAG = "BuddyPhonesHelper";
    private DatabaseHelper databaseHelper;
    private Context context;

    public BuddyPhonesHelper(Context context) {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
    }

    public boolean sendSmsToAllBuddies(String body) {
        for(BuddyPhone buddyPhone : getAllBuddyPhones()) {
            buddyPhone.sendSms(body, SmsHelper.NO_DECORATION);
        }
        return true;
    }

    public boolean sendSmsToAllBuddies(String body, int options) {
        for(BuddyPhone buddyPhone : getAllBuddyPhones()) {
            buddyPhone.sendSms(body, options);
        }
        return true;
    }

    public ArrayList<String> getAllBuddyPhoneNumbers() {
        ArrayList<String> numbers = new ArrayList<>();
        for(BuddyPhone buddyPhone: getAllBuddyPhones()) {
           numbers.add(buddyPhone.getPhoneNumber());
        }
        return numbers;
    }

    public ArrayList<BuddyPhone> getAllBuddyPhones() {
        return databaseHelper.getBuddyPhones();
    }
}
