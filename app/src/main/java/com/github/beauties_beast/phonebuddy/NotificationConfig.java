package com.github.beauties_beast.phonebuddy;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

public class NotificationConfig extends AppCompatActivity {
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
        }

        return super.onOptionsItemSelected(item);
    }

    ArrayList<Card> cards;
    List packageList;

    public void initCards() {
        cards = new ArrayList<>();
        initNotificationCards();
        renderCards();
    }

    public void initNotificationCards() {
        PackageManagerHelper packageManagerHelper = new PackageManagerHelper(getBaseContext());
        List<ApplicationInfo> packages = packageManagerHelper.getAllPackages();
        Log.d(TAG, String.format("NotificationConfig packages %s size", String.valueOf(packages.size())));
        for(ApplicationInfo applicationInfo : packages) {
            Card card = new Card(getBaseContext());
            CardHeader cardHeader = new CardHeader(getBaseContext());
            cardHeader.setTitle(packageManagerHelper.getAppName(applicationInfo.packageName));
            card.addCardHeader(cardHeader);
            card.setTitle(applicationInfo.packageName);
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
