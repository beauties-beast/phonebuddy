package com.github.beauties_beast.phonebuddy;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.provider.Settings;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

public class NotificationConfigActivity extends AppCompatActivity {
    private static final String TAG = "NotifificationConfig";

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_config);

        databaseHelper = new DatabaseHelper(getBaseContext());
        initCards();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCards();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        } else if (id == R.id.notification_config_add) {
            Intent intent = new Intent();
            intent.setClass(getBaseContext(), AddNotificationConfigActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    ArrayList<Card> cards;
    List packageList;

    public void initCards() {
        cards = new ArrayList<>();
        initNotificationInfo();
        initNotificationCards();
        renderCards();
    }

    public void initNotificationInfo() {
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        String enabledNotificationListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
        String packageName = getBaseContext().getPackageName();

        // check to see if the enabledNotificationListeners String contains our package name
        if (enabledNotificationListeners == null || !enabledNotificationListeners.contains(packageName))
        {
            // in this situation we know that the user has not granted the app the Notification access permission
//            throw new Exception();
            Card card = new Card(getBaseContext());
            CardHeader cardHeader = new CardHeader(getBaseContext());
            cardHeader.setTitle("You need to enable Notification Access for PhoneBuddy.");
            card.addCardHeader(cardHeader);
            card.setTitle("In order to push notifications from your smartphone to your buddy phone, you need to give PhoneBuddy notification access." +
                    "Tap on this card to enable access.");
            card.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
//                    Intent intent = new Intent();
//                    intent.setClass(getBaseContext(), NotificationConfigActivity.class);
//                    startActivity(intent);
                    startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                }
            });
            cards.add(card);
        }
        else
        {
//            doSomethingThatRequiresNotificationAccessPermission();
        }
    }

    public void initNotificationCards() {
        PackageManagerHelper packageManagerHelper = new PackageManagerHelper(getBaseContext());
        List<ApplicationInfo> packages = packageManagerHelper.getAllPackages();
        Log.d(TAG, String.format("NotificationConfigActivity packages %s size", String.valueOf(packages.size())));
        for(final ApplicationInfo applicationInfo : packages) {
            if(databaseHelper.getNotificationConfig().contains(applicationInfo.packageName)) {
                Card card = new Card(getBaseContext());
                final CardHeader cardHeader = new CardHeader(getBaseContext());
                cardHeader.setTitle(String.format("%s", packageManagerHelper.getAppName(applicationInfo.packageName)));
                databaseHelper.addNotificationConfig(applicationInfo.packageName);
                card.setBackgroundColorResourceId(R.color.normal);
                card.setTitle(String.format("%s", applicationInfo.packageName));
                cardHeader.setButtonOverflowVisible(true);
                CardHeader.OnClickCardHeaderPopupMenuListener listener = new CardHeader.OnClickCardHeaderPopupMenuListener() {
                    @Override
                    public void onMenuItemClick(BaseCard baseCard, MenuItem menuItem) {
                        if (menuItem.getTitle().equals("Remove")) {
                            if (databaseHelper.getNotificationConfig().contains(applicationInfo.packageName)) {
                                databaseHelper.removeNotificationConfig(applicationInfo.packageName);
                                Toast.makeText(getBaseContext(), String.format("Disabled forwarding for %s", cardHeader.getTitle()), Toast.LENGTH_LONG).show();
                            }
                            Intent intent = new Intent(getBaseContext(), NotificationConfigActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                };
                cardHeader.setPopupMenu(R.menu.menu_notification_config_item, listener);
                card.addCardHeader(cardHeader);
                cards.add(card);
            }
        }
        if(cards.isEmpty()) {
            Log.d(TAG, "NotificationConfigActivity No applications.");
            Card card = new Card(getBaseContext());
            CardHeader cardHeader = new CardHeader(getBaseContext());
            cardHeader.setTitle("No applications selected.");
            card.addCardHeader(cardHeader);
            card.setTitle("Tap on the + on the top right to add an application to forward notifications from!");
            cards.add(card);
        }
    }

    private void renderCards() {
        CardArrayRecyclerViewAdapter cardArrayRecyclerViewAdapter = new CardArrayRecyclerViewAdapter(getBaseContext(), cards);

        CardRecyclerView cardRecyclerView = (CardRecyclerView) findViewById(R.id.notificationconfig_recyclerview);
        cardRecyclerView.setHasFixedSize(false);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        if (cardRecyclerView != null) {
            cardRecyclerView.setAdapter(cardArrayRecyclerViewAdapter);
        }
    }
}
