package com.github.beauties_beast.phonebuddy;

import android.content.Context;

/**
 * Created by boggs on 10/10/15.
 */
public class SmsForwarder implements ForwarderInterface {

    @Override
    public boolean parse(Object object, Context context) {
        Sms sms = (Sms) object;
        sms.formatBodyForBuddyRecepient(context);
        return action(sms, context);
    }

    @Override
    public boolean action(Object options, Context context) {
        Sms sms = (Sms) options;
        BuddyPhonesHelper buddyPhonesHelper = new BuddyPhonesHelper(context);
        buddyPhonesHelper.sendSmsToAllBuddies(sms.getBody());
        return true;
    }

    @Override
    public boolean response(Object options, Context context) {
        return false;
    }
}
