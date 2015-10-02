package com.github.beauties_beast.phonebuddy;

/**
 * Created by boggs on 9/30/15.
 */
public class BuddyPhone {
    private static final String TAG = "BuddyPhone";
    private String nickName;
    private String phoneNumber;

    public BuddyPhone(String nickName, String phoneNumber) {
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
    }

    public boolean sendSms(String body) {
        return SmsHelper.sendSms(phoneNumber, body);
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
