package com.github.beauties_beast.phonebuddy;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by boggs on 9/30/15.
 */
public class SmsHelper {
    private static final String TAG = "SmsHelper";
    public static final int NO_DECORATION = 1;
    public static final int WITH_HEADER = 2;
    public static final int WITH_FOOTER = 3;

    public static boolean sendSms(String phoneNumber, String body) {
        return sendSms(phoneNumber, body, NO_DECORATION);
    }

    public static boolean sendSms(String phoneNumber, String body, int options) {
        switch(options) {
            case WITH_HEADER:
                body = "PhoneBuddy:\n" + body;
                break;
            case WITH_FOOTER:
                body = body + "\nSent from PhoneBuddy";
                break;
            default:
                break;
        }
        try {
            SmsManager smsManager = SmsManager.getDefault();
            Log.d(TAG, String.format("sendSms %s %s", phoneNumber, body));
            smsManager.sendTextMessage(phoneNumber, null, body, null, null);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    public static String getPhoneNumber(String name, Context context) {
        // TODO: Better number getter. Fix this ugly code. Only accepts complete matches.
        ArrayList<String> numbers = new ArrayList<>();

    	Cursor cursor = null;
    	try {
    	    cursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI,
    	            new String [] { ContactsContract.Data.RAW_CONTACT_ID },
    	            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME + "=? AND "
    	                + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE + "'",
    	            new String[] { name },  null);
    	    if (cursor != null && cursor.moveToFirst()) {
    	        do {
    	            String rawContactId = cursor.getString(0);
    	            Cursor phoneCursor = null;
    	            try {
    	                phoneCursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI,
    	                        new String[] {ContactsContract.Data._ID, ContactsContract.CommonDataKinds.Phone.NUMBER},
    	                        ContactsContract.Data.RAW_CONTACT_ID + "=?" + " AND "
    	                                + ContactsContract.CommonDataKinds.Phone.TYPE + "=" + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE + " AND "
    	                                + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'",
    	                                new String[] {rawContactId}, null);

    	                if (phoneCursor != null && phoneCursor.moveToFirst()) {
    	                    String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
    	                    numbers.add(number.trim().replace("-", "").replace(" ", ""));
    	                }
    	            }
    	            finally {
    	                if (phoneCursor != null)
    	                    phoneCursor.close();
    	            }
    	        }
    	        while (cursor.moveToNext());
    	    }
    	} catch (NullPointerException e) {

		}
    	finally {
    	    if (cursor != null) {
    	        cursor.close();
    	    }
    	}
            	//better number handling
    	if (numbers.isEmpty())
    	{
    		if(name.charAt(0) == '+' || name.charAt(0) == '0')
    	    {
        		numbers.add(name.trim().replace("-", "").replace(" ", ""));
    	    }
    	}

		Log.d(TAG, String.format("%s getPhoneNumber %s %s", TAG, name, numbers.isEmpty() ? "empty" : numbers.toString()));
    	return numbers.get(0);
    }

    public static String getContactName(String number, Context context)
    {
        // TODO: Better contact getter. Fix this ugly code.
        String name = null;

        String[] projection = new String[] {
                ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.PhoneLookup._ID };

        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        Cursor cursor = context.getContentResolver().query(contactUri, projection, null, null, null);

        if(cursor != null)
        {
            if (cursor.moveToFirst())
            	name = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            else
                name = number;
            cursor.close();
        }

        Log.d(TAG, String.format("%s getContactName %s %s", TAG, number, name));
        return name;
    }
}
