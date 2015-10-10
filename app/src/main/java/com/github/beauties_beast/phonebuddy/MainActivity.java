package com.github.beauties_beast.phonebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    DatabaseHelper databaseHelper;

    SwitchCompat actionBarSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(getBaseContext());
        databaseHelper.resetNotifications();

        ServiceManager.getInstance().initContext(getBaseContext());
        ServiceManager.getInstance().initDatabaseHelper();

        initCards();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.mainSwitch);
        item.setActionView(R.layout.action_bar_switch_layout);

        actionBarSwitch = (SwitchCompat) menu.findItem(R.id.mainSwitch).getActionView().findViewById(R.id.switchForActionBar);
        actionBarSwitch.setChecked(ServiceManager.getInstance().isActive());
        actionBarSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean changed = ServiceManager.getInstance().setActive(isChecked);
                if(isChecked) {
                    if(changed)
                        Toast.makeText(getBaseContext(), String.format("PhoneBuddy is now %s.", isChecked ? "active" : "disabled"), Toast.LENGTH_LONG).show();
                    else {
                        buttonView.setChecked(false);
                        Toast.makeText(getBaseContext(), String.format("There are no paired buddy phones. Pair a phone before enabling PhoneBuddy.", isChecked ? "active" : "disabled"), Toast.LENGTH_LONG).show();
                    }
                } else {
                    if(changed)
                        Toast.makeText(getBaseContext(), String.format("PhoneBuddy is now %s.", isChecked ? "active" : "disabled"), Toast.LENGTH_LONG).show();
                }
            }
        });
//        actionBarSwitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.
//                if (v.isActivated()) {
//                    Toast.makeText(getBaseContext(), "fuck activated", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getBaseContext(), "fuck not activated", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        return true;
    }

    ArrayList<Card> cards = new ArrayList<>();

    public void initCards() {
        initGetStartedCard();
        initManageBuddyPhonesCard();
        initShowActivityLog();
        initSettings();
        renderCards();
    }

    private void initGetStartedCard() {
        Card card = new Card(getBaseContext());
        CardHeader cardHeader = new CardHeader(getBaseContext());
        cardHeader.setTitle("Getting Started");
        card.addCardHeader(cardHeader);
        card.setTitle("Get to know more about what PhoneBuddy can do!");
        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), GettingStartedActivity.class);
                startActivity(intent);
            }
        });
        cards.add(card);
    }

    private void initManageBuddyPhonesCard() {
        Card card = new Card(getBaseContext());
        CardHeader cardHeader = new CardHeader(getBaseContext());
        cardHeader.setTitle("Manage Buddy Phones");
        card.addCardHeader(cardHeader);
        card.setTitle("Add or remove buddy phones paired with this smartphone.");
        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), ManageBuddyPhonesActivity.class);
                startActivity(intent);
            }
        });
        cards.add(card);
    }

    private void initShowActivityLog() {
        Card card = new Card(getBaseContext());
        CardHeader cardHeader = new CardHeader(getBaseContext());
        cardHeader.setTitle("Show Activity Log");
        card.addCardHeader(cardHeader);
        card.setTitle("Check the interactions between this smartphone and your buddy phones.");
        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), ActivityLogActivity.class);
                startActivity(intent);
            }
        });
        cards.add(card);
    }

    private void initSettings() {
        Card card = new Card(getBaseContext());
        CardHeader cardHeader = new CardHeader(getBaseContext());
        cardHeader.setTitle("Settings");
        card.addCardHeader(cardHeader);
        card.setTitle("Change PhoneBuddy's settings.");
        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        cards.add(card);
    }

    private void renderCards() {
        CardArrayRecyclerViewAdapter cardArrayRecyclerViewAdapter = new CardArrayRecyclerViewAdapter(getBaseContext(), cards);

        CardRecyclerView cardRecyclerView = (CardRecyclerView) findViewById(R.id.main_recyclerview);
        cardRecyclerView.setHasFixedSize(false);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        if (cardRecyclerView != null) {
            cardRecyclerView.setAdapter(cardArrayRecyclerViewAdapter);
        }
    }
}
