package com.github.beauties_beast.phonebuddy;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerArray;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardViewNative;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    DatabaseHelper databaseHelper;

//    Button addBuddyButton;
//    EditText addBuddyEditText;
//    ListView buddyPhoneListView;

    ArrayAdapter<BuddyPhone> buddyPhoneArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(getBaseContext());
        initCards();

//        addBuddyButton = (Button) findViewById(R.id.btn_add_buddy);
//        buddyPhoneListView = (ListView) findViewById(R.id.lv_buddy_phones);

//        addBuddyButton.setOnClickListener(addBuddy);

//        Card card = new Card(getBaseContext());
//
//        CardHeader header = new CardHeader(getBaseContext());
//
//        header.setTitle("Getting Started");
//
//        card.addCardHeader(header);
//
//        CardViewNative cardView = (CardViewNative) findViewById(R.id.carddemo);
//
//        cardView.setCard(card);
//        cardView.
    }

//    OnClickListener addBuddy = new OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Log.d(TAG, "fuck1");
//            addBuddyEditText = (EditText) findViewById(R.id.et_add_buddy);
//
//            Log.d(TAG, "fuck2");
//            BuddyPhone buddyPhone = new BuddyPhone("", addBuddyEditText.getText().toString());
//            databaseHelper.addBuddyPhone(buddyPhone);
//
//            Log.d(TAG, "fuck3");
//            buddyPhoneArrayAdapter = new ArrayAdapter<BuddyPhone>(getBaseContext(), R.layout.buddy_phone_list_item);
//            buddyPhoneArrayAdapter.addAll(databaseHelper.getBuddyPhones());
//            buddyPhoneListView.setAdapter(buddyPhoneArrayAdapter);
//            Log.d(TAG, "fuck4");
//        }
//    };

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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
        cards.add(card);
    }

    private void initManageBuddyPhonesCard() {
        Card card = new Card(getBaseContext());
        CardHeader cardHeader = new CardHeader(getBaseContext());
        cardHeader.setTitle("Manage Buddy Phones");
        card.addCardHeader(cardHeader);
        card.setTitle("Add or remove buddy phones paired with this smartphone.");
        cards.add(card);
    }

    private void initShowActivityLog() {
        Card card = new Card(getBaseContext());
        CardHeader cardHeader = new CardHeader(getBaseContext());
        cardHeader.setTitle("Show Activity Log");
        card.addCardHeader(cardHeader);
        card.setTitle("Check the interactions between this smartphone and your buddy phones.");
        cards.add(card);
    }

    private void initSettings() {
        Card card = new Card(getBaseContext());
        CardHeader cardHeader = new CardHeader(getBaseContext());
        cardHeader.setTitle("Settings");
        card.addCardHeader(cardHeader);
        card.setTitle("Change PhoneBuddy's settings. Wow boggs naubusan ka na ba ng Ingles punyeta");
        cards.add(card);
    }

    private void renderCards() {
        CardArrayRecyclerViewAdapter cardArrayRecyclerViewAdapter = new CardArrayRecyclerViewAdapter(getBaseContext(), cards);

        CardRecyclerView cardRecyclerView = (CardRecyclerView) findViewById(R.id.carddemo_recyclerview);
        cardRecyclerView.setHasFixedSize(false);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        if (cardRecyclerView != null) {
            cardRecyclerView.setAdapter(cardArrayRecyclerViewAdapter);
        }
//        CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(this, cards);
//        CardListView listView = (CardListView) findViewById(R.id.myList);
//        if(listView !=  null) {
//            listView.setAdapter(cardArrayAdapter);
//        }
    }
}
