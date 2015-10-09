package com.github.beauties_beast.phonebuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by boggs on 10/2/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "DatabaseHelper";

    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "PhoneBuddy.db";

    public static final String BUDDY_TABLE_NAME = "buddies_list";
    public static final String BUDDY_COLUMN_ID = "id";
    public static final String BUDDY_COLUMN_NAME = "name";
    public static final String BUDDY_COLUMN_NUMBER = "number";
    public static final String BUDDY_COLUMN_CREATED_AT = "created_at";

    public static final String NOTIFICATION_CONFIG_TABLE_NAME = "notification_config";
    public static final String NOTIFICATION_CONFIG_COLUMN_ID = "id";
    public static final String NOTIFICATION_CONFIG_COLUMN_PACKAGE_NAME = "package_name";
    public static final String NOTIFICATION_CONFIG_CREATED_AT = "created_at";

    public static final String NOTIFICATION_LOG_TABLE_NAME = "notification_log";
    public static final String NOTIFICATION_LOG_COLUMN_ID = "id";
    public static final String NOTIFICATION_LOG_COLUMN_PACKAGE_NAME = "package_name";
    public static final String NOTIFICATION_LOG_COLUMN_TICKER_TEXT= "ticker_text";
    public static final String NOTIFICATION_LOG_COLUMN_TITLE= "title";
    public static final String NOTIFICATION_LOG_COLUMN_TEXT= "text";
    public static final String NOTIFICATION_LOG_COLUMN_CREATED_AT = "created_at";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, String.format("Database create version %s.", DATABASE_VERSION));
        initializeBuddyTable(db);
        initializeNotificationConfigTable(db);
        initializeNotificationLogTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("Database upgrade from %s to %s.", oldVersion, newVersion));
        dropBuddyTable(db);
        dropNotificationConfigPreferenceTable(db);
        dropNotificationLogTable(db);
        onCreate(db);
    }

    public void initializeBuddyTable(SQLiteDatabase db) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("CREATE TABLE %s ", BUDDY_TABLE_NAME));
        sql.append("(");
        sql.append(String.format("%s %s, ", BUDDY_COLUMN_ID, "INTEGER PRIMARY KEY"));
        sql.append(String.format("%s %s, ", BUDDY_COLUMN_NAME, "TEXT"));
        sql.append(String.format("%s %s, ", BUDDY_COLUMN_NUMBER, "TEXT"));
        sql.append(String.format("%s %s ", BUDDY_COLUMN_CREATED_AT, "DATETIME DEFAULT (DATETIME (CURRENT_TIMESTAMP, 'LOCALTIME'))"));
        sql.append(")");
        Log.d(TAG, String.format("Exec: %s", sql.toString()));
        db.execSQL(sql.toString());
    }

    public void dropBuddyTable(SQLiteDatabase db) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", BUDDY_TABLE_NAME));
    }

    public void initializeNotificationConfigTable(SQLiteDatabase db) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("CREATE TABLE %s ", NOTIFICATION_CONFIG_TABLE_NAME));
        sql.append("(");
        sql.append(String.format("%s %s, ", NOTIFICATION_CONFIG_COLUMN_ID, "INTEGER PRIMARY KEY"));
        sql.append(String.format("%s %s, ", NOTIFICATION_CONFIG_COLUMN_PACKAGE_NAME, "TEXT"));
        sql.append(String.format("%s %s ", NOTIFICATION_CONFIG_CREATED_AT, "DATETIME DEFAULT (DATETIME (CURRENT_TIMESTAMP, 'LOCALTIME'))"));
        sql.append(")");
        Log.d(TAG, String.format("Exec: %s", sql.toString()));
        db.execSQL(sql.toString());
    }

    public void dropNotificationConfigPreferenceTable(SQLiteDatabase db) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", NOTIFICATION_CONFIG_TABLE_NAME));
    }

    public void initializeNotificationLogTable(SQLiteDatabase db) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("CREATE TABLE %s ", NOTIFICATION_LOG_TABLE_NAME));
        sql.append("(");
        sql.append(String.format("%s %s, ", NOTIFICATION_LOG_COLUMN_ID, "INTEGER PRIMARY KEY"));
        sql.append(String.format("%s %s, ", NOTIFICATION_LOG_COLUMN_PACKAGE_NAME, "TEXT"));
        sql.append(String.format("%s %s, ", NOTIFICATION_LOG_COLUMN_TICKER_TEXT, "TEXT"));
        sql.append(String.format("%s %s, ", NOTIFICATION_LOG_COLUMN_TITLE, "TEXT"));
        sql.append(String.format("%s %s, ", NOTIFICATION_LOG_COLUMN_TEXT, "TEXT"));
        sql.append(String.format("%s %s ", NOTIFICATION_LOG_COLUMN_CREATED_AT, "DATETIME DEFAULT (DATETIME (CURRENT_TIMESTAMP, 'LOCALTIME'))"));
        sql.append(")");
        Log.d(TAG, String.format("Exec: %s", sql.toString()));
        db.execSQL(sql.toString());
    }

    public void dropNotificationLogTable(SQLiteDatabase db) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", NOTIFICATION_LOG_COLUMN_CREATED_AT));
    }

    public void addBuddyPhone(BuddyPhone buddyPhone) {
        Log.d(TAG, String.format("Database addBuddyPhone()"));
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BUDDY_COLUMN_NAME, buddyPhone.getNickName());
        values.put(BUDDY_COLUMN_NUMBER, buddyPhone.getPhoneNumber());
        db.insert(BUDDY_TABLE_NAME, null, values);
        db.close();
    }

    public void removeBuddyPhone(String number) {
        removeBuddyPhone(new BuddyPhone("", number));
    }

    public void removeBuddyPhone(BuddyPhone buddyPhone) {
        Log.d(TAG, String.format("Database removeBuddyPhone()"));
        SQLiteDatabase db = getWritableDatabase();
        db.delete(BUDDY_TABLE_NAME, BUDDY_COLUMN_NUMBER + " =? ", new String[]{String.valueOf(buddyPhone.getPhoneNumber())});
        db.close();
    }

    public ArrayList<BuddyPhone> getBuddyPhones() {
        Log.d(TAG, String.format("Database getBuddyPhones()"));

        SQLiteDatabase db = getReadableDatabase();
        ArrayList<BuddyPhone> buddyPhones = new ArrayList<BuddyPhone>();

        db.rawQuery(String.format("SELECT * FROM %s ", BUDDY_TABLE_NAME), null);

        Cursor cursor = db.query(BUDDY_TABLE_NAME,
                new String[]{BUDDY_COLUMN_NAME, BUDDY_COLUMN_NUMBER, BUDDY_COLUMN_CREATED_AT},
                null, // id = ?
                null,
                null,
                null,
                null
        );

        if(cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(BUDDY_COLUMN_NAME));
                String number = cursor.getString(cursor.getColumnIndex(BUDDY_COLUMN_NUMBER));
                String createdAtRaw = cursor.getString(cursor.getColumnIndex(BUDDY_COLUMN_CREATED_AT));
                SimpleDateFormat createdAtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat createdAtFormatLocal = new SimpleDateFormat(createdAtFormat.toLocalizedPattern());
                Date createdAt;
                try {
                    createdAt = createdAtFormatLocal.parse(createdAtRaw);
                } catch (ParseException e) {
                    Log.d(TAG, String.format("Database getBuddyPhones() Date parse failed."));
                    createdAt = new Date();
                }
                buddyPhones.add(new BuddyPhone(name, number, createdAt));
            } while(cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return buddyPhones;
    }

    public void addNotification(Notification notification) {
        Log.d(TAG, String.format("Database addNotification()"));
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTIFICATION_LOG_COLUMN_PACKAGE_NAME, notification.getPackageName());
        values.put(NOTIFICATION_LOG_COLUMN_TEXT, notification.getText());
        values.put(NOTIFICATION_LOG_COLUMN_TICKER_TEXT, notification.getTickerText());
        values.put(NOTIFICATION_LOG_COLUMN_TITLE, notification.getTitle());
        db.insert(NOTIFICATION_LOG_TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Notification> getNotifications() {
        Log.d(TAG, String.format("Database getNotifications()"));

        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Notification> notifications = new ArrayList<>();

        db.rawQuery(String.format("SELECT * FROM %s ", NOTIFICATION_LOG_TABLE_NAME), null);

        Cursor cursor = db.query(NOTIFICATION_LOG_TABLE_NAME,
                new String[]{ NOTIFICATION_LOG_COLUMN_ID, NOTIFICATION_LOG_COLUMN_PACKAGE_NAME, NOTIFICATION_LOG_COLUMN_TICKER_TEXT, NOTIFICATION_LOG_COLUMN_TEXT, NOTIFICATION_LOG_COLUMN_CREATED_AT, NOTIFICATION_LOG_COLUMN_TITLE },
                null, // id = ?
                null,
                null,
                null,
                String.format("%s DESC", NOTIFICATION_LOG_COLUMN_CREATED_AT)
        );

        if(cursor.moveToFirst()) {
            do {
                String packageName = cursor.getString(cursor.getColumnIndex(NOTIFICATION_LOG_COLUMN_PACKAGE_NAME));
                String tickerText= cursor.getString(cursor.getColumnIndex(NOTIFICATION_LOG_COLUMN_TICKER_TEXT));
                String title = cursor.getString(cursor.getColumnIndex(NOTIFICATION_LOG_COLUMN_TITLE));
                String text = cursor.getString(cursor.getColumnIndex(NOTIFICATION_LOG_COLUMN_TEXT));
                String createdAtRaw = cursor.getString(cursor.getColumnIndex(NOTIFICATION_LOG_COLUMN_CREATED_AT));
                SimpleDateFormat createdAtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat createdAtFormatLocal = new SimpleDateFormat(createdAtFormat.toLocalizedPattern());
                Date createdAt;
                try {
                    createdAt = createdAtFormatLocal.parse(createdAtRaw);
                } catch (ParseException e) {
                    Log.d(TAG, String.format("Database getBuddyPhones() Date parse failed."));
                    createdAt = new Date();
                }
                notifications.add(new Notification(packageName, tickerText, title, text, createdAt));
            } while(cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return notifications;
    }

    public void resetNotifications() {
        Log.d(TAG, String.format("Database resetNotifications()"));
        SQLiteDatabase db = getWritableDatabase();
        db.delete(NOTIFICATION_LOG_TABLE_NAME, null, null);
        db.close();
    }
}
