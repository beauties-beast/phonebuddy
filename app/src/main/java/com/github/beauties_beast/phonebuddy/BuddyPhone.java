package com.github.beauties_beast.phonebuddy;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by boggs on 9/30/15.
 */
public class BuddyPhone {
    private static final String TAG = "BuddyPhone";
    private String nickName;
    private String phoneNumber;
    private Date createdAt;

    public BuddyPhone(String nickName, String phoneNumber) {
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
    }

    public BuddyPhone(String nickName, String phoneNumber, Date createdAt) {
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    public String getSimpleCreatedAt() {
        SimpleDateFormat createdAtFormat = new SimpleDateFormat("MMM d, yyyy 'at' h:mm a");
        return createdAtFormat.format(createdAt).toString();
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
