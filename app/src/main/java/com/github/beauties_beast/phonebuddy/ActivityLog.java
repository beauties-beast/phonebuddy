package com.github.beauties_beast.phonebuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

public class ActivityLog extends AppCompatActivity {
    public final static String TAG = "ActivityLog";

    DatabaseHelper databaseHelper;
    ArrayList<Card> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_log);

        databaseHelper = new DatabaseHelper(getBaseContext());
        initCards();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void initCards() {
        cards = new ArrayList<>();
        initActivityLogCards();
        renderCards();
    }

    public void initActivityLogCards() {
        ArrayList<Notification> notifications = databaseHelper.getNotifications();
        if (notifications.size() > 0) {
            Log.d(TAG, String.format("ActivityLog %s notifications.", String.valueOf(notifications.size())));
            for (Notification notification : notifications) {
                String appName = notification.getAppName(getBaseContext());
                Card card = new Card(getBaseContext());
                final CardHeader cardHeader = new CardHeader(getBaseContext());
                cardHeader.setTitle(appName);
                card.addCardHeader(cardHeader);
                card.setTitle(String.format("Received notification \"%s\" from %s on %s", notification.getPreferredText(), appName, notification.getSimpleCreatedAt()));
                cards.add(card);
            }
        } else {
            Log.d(TAG, "ActivityLog No activities.");
            Card card = new Card(getBaseContext());
            CardHeader cardHeader = new CardHeader(getBaseContext());
            cardHeader.setTitle("No recent activities.");
            card.addCardHeader(cardHeader);
            card.setTitle("Activities like SMS and Notifications will be displayed here as soon as they come in");
            cards.add(card);
        }
    }

    private void renderCards() {
        CardArrayRecyclerViewAdapter cardArrayRecyclerViewAdapter = new CardArrayRecyclerViewAdapter(getBaseContext(), cards);

        CardRecyclerView cardRecyclerView = (CardRecyclerView) findViewById(R.id.activity_log_reyclerview);
        cardRecyclerView.setHasFixedSize(false);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        if (cardRecyclerView != null) {
            cardRecyclerView.setAdapter(cardArrayRecyclerViewAdapter);
        }
    }
}
