package com.github.beauties_beast.phonebuddy;

/**
 * Created by boggs on 10/9/15.
 */
public class CommandParser {
//    private final String[] keywords = {"SMS", "SOS", "LOCATION", "STATUS"};

    public CommandParser() {

    }

    public static boolean parse(String sms) {
        String[] smsArr = sms.split(" ");
        String keyword = smsArr[0];
        boolean parsed = true;
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
