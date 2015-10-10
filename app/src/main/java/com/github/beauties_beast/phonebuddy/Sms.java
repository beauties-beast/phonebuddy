package com.github.beauties_beast.phonebuddy;

import android.content.Context;

/**
 * Created by boggs on 10/10/15.
 */
public class Sms {
    private String number;
    private String body;

    public Sms(String number, String body) {
        this.number = number;
        this.body = body;
    }

    public String getContactName(Context context) {
        return SmsHelper.getContactName(number, context);
    }

    public void formatBodyForBuddyRecepient(Context context) {
        StringBuilder tempBody = new StringBuilder();
        tempBody.append(String.format("Received SMS from %s (%s):\n", getContactName(context), number));
        tempBody.append(body);
        body = tempBody.toString();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
