package com.github.beauties_beast.phonebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

public class ManageBuddyPhones extends AppCompatActivity {
    public final static String TAG = "ManageBuddyPhones";

    DatabaseHelper databaseHelper;
    ArrayList<Card> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_buddy_phones);
        databaseHelper = new DatabaseHelper(getBaseContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
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

        return super.onOptionsItemSelected(item);
    }

    public void initCards() {
        Log.d(TAG, "ManageBuddyPhones initCards()");

        cards = new ArrayList<>();
        initBuddyPhones();
        renderCards();
    }

    private void initBuddyPhones() {
        ArrayList<BuddyPhone> buddyPhones = databaseHelper.getBuddyPhones();
        if (buddyPhones.size() > 0) {
            Log.d(TAG, String.format("ManageBuddyPhones %s buddy phones.", String.valueOf(buddyPhones.size())));
            for (BuddyPhone buddyPhone : buddyPhones) {
                Card card = new Card(getBaseContext());
                final CardHeader cardHeader = new CardHeader(getBaseContext());
//                cardHeader.setTitle(!buddyPhone.getNickName().equals("") ? buddyPhone.getNickName() : "Buddy Phone");
                cardHeader.setTitle(buddyPhone.getPhoneNumber());
                cardHeader.setButtonOverflowVisible(true);
                CardHeader.OnClickCardHeaderPopupMenuListener popupMenuListener = new CardHeader.OnClickCardHeaderPopupMenuListener() {
                    @Override
                    public void onMenuItemClick(BaseCard baseCard, MenuItem menuItem) {
                        if (menuItem.getTitle().equals("Remove")) {
                            databaseHelper.removeBuddyPhone(cardHeader.getTitle());
                            String toastMessage = String.format("Successfully removed %s.", baseCard.getTitle());
                            Toast.makeText(getBaseContext(), toastMessage, Toast.LENGTH_LONG).show();
                        }
                        Intent intent = new Intent(getBaseContext(), ManageBuddyPhones.class);
                        startActivity(intent);
                        finish();
                    }
                };
                cardHeader.setPopupMenu(R.menu.menu_manage_buddy_phone_item, popupMenuListener);
                card.addCardHeader(cardHeader);
                card.setTitle(String.format("Added on %s", buddyPhone.getSimpleCreatedAt()));
                cards.add(card);
            }
        } else {
            Log.d(TAG, "ManageBuddyPhones No paired buddy phones.");
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
