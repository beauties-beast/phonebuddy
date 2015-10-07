package com.github.beauties_beast.phonebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

public class ManageBuddyPhones extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_buddy_phones);
        databaseHelper = new DatabaseHelper(getBaseContext());
        initCards();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manage_buddy_phones, menu);
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
        } else if (id == R.id.manage_buddy_phones_add) {
            Intent intent = new Intent();
            intent.setClass(getBaseContext(), AddBuddyPhone.class);
            startActivity(intent);
        }
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    ArrayList<Card> cards = new ArrayList<>();

    public void initCards() {
        initBuddyPhones();
        renderCards();
    }

    private void initBuddyPhones() {
        ArrayList<BuddyPhone> buddyPhones = databaseHelper.getBuddyPhones();
        if(buddyPhones.size() > 0) {
            for(BuddyPhone buddyPhone : buddyPhones) {
                Card card = new Card(getBaseContext());
                CardHeader cardHeader = new CardHeader(getBaseContext());
                cardHeader.setTitle(buddyPhone.getNickName());
                card.addCardHeader(cardHeader);
                card.setTitle(buddyPhone.getPhoneNumber());
                cards.add(card);
            }
        } else {
            Card card = new Card(getBaseContext());
            CardHeader cardHeader = new CardHeader(getBaseContext());
            cardHeader.setTitle("No paired buddy phones.");
            card.addCardHeader(cardHeader);
            card.setTitle("Tap on the + on the top right to pair a new buddy phone!");
            cards.add(card);
        }
    }

    private void renderCards() {
        CardArrayRecyclerViewAdapter cardArrayRecyclerViewAdapter = new CardArrayRecyclerViewAdapter(getBaseContext(), cards);

        CardRecyclerView cardRecyclerView = (CardRecyclerView) findViewById(R.id.manage_buddy_phones_recyclerview);
        cardRecyclerView.setHasFixedSize(false);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        if (cardRecyclerView != null) {
            cardRecyclerView.setAdapter(cardArrayRecyclerViewAdapter);
        }
    }
}
