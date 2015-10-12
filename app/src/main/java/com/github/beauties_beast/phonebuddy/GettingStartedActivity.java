package com.github.beauties_beast.phonebuddy;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

public class GettingStartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_getting_started);
        initCards();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_getting_started, menu);
//        return true;
//    }
//
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

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    ArrayList<Card> cards = new ArrayList<>();

    public void initCards() {
//        for(int i = 0; i < 10; i++)
//            initHelpItem1();
        initHelpItem0();
        initHelpItem1();
        initHelpItem2();
        initHelpItem3();
        initHelpItem4();
        initHelpItem5();
        initHelpItem6();
        renderCards();
    }

    private void initHelpItem0() {
        Card card = new Card(getBaseContext());
        CardHeader cardHeader = new CardHeader(getBaseContext());
        cardHeader.setTitle("I want to learn more about PhoneBuddy, how it works, and how I can fully unlock its potential!");
        card.addCardHeader(cardHeader);
        card.setTitle("You can check out our primer which contains all the information you're looking for - including PhoneBuddy's functionality, commands, and information about the app itself. http://tinyurl.com/PhoneBuddyPrimer");
        cards.add(card);
    }

    private void initHelpItem1() {
        Card card = new Card(getBaseContext());
        CardHeader cardHeader = new CardHeader(getBaseContext());
        cardHeader.setTitle("What is PhoneBuddy?");
        cardHeader.setButtonExpandVisible(true);
        card.addCardHeader(cardHeader);
        CardExpand cardExpand = new CardExpand(getBaseContext());
        cardExpand.setTitle("PhoneBuddy lets you interface with your smartphone from any SMS-capable device - which will be referred to as your buddy phone.");
        card.addCardExpand(cardExpand);
        cards.add(card);
    }

    private void initHelpItem2() {
        Card card = new Card(getBaseContext());
        CardHeader cardHeader = new CardHeader(getBaseContext());
        cardHeader.setTitle("How can I pair a buddy phone?");
        cardHeader.setButtonExpandVisible(true);
        card.addCardHeader(cardHeader);
        CardExpand cardExpand = new CardExpand(getBaseContext());
        cardExpand.setTitle("Under 'Manage Buddy Phones', tap on the + on the top right to pair a new buddy phone. Enter your buddy phone's number and a verification code will be sent.");
        card.addCardExpand(cardExpand);
        cards.add(card);
    }

    private void initHelpItem3() {
        Card card = new Card(getBaseContext());
        CardHeader cardHeader = new CardHeader(getBaseContext());
        cardHeader.setTitle("How can I remove my buddy phone?");
        cardHeader.setButtonExpandVisible(true);
        card.addCardHeader(cardHeader);
        CardExpand cardExpand = new CardExpand(getBaseContext());
        cardExpand.setTitle("Under 'Manage Buddy Phones', choose the buddy phone and press delete.");
        card.addCardExpand(cardExpand);
        cards.add(card);
    }

    private void initHelpItem4() {
        Card card = new Card(getBaseContext());
        CardHeader cardHeader = new CardHeader(getBaseContext());
        cardHeader.setTitle("How can I choose what notifications to receive?");
        cardHeader.setButtonExpandVisible(true);
        card.addCardHeader(cardHeader);
        CardExpand cardExpand = new CardExpand(getBaseContext());
        cardExpand.setTitle("Under 'Settings', you can choose which applications would be able to forward notifications to your buddy phone.");
                card.addCardExpand(cardExpand);
        cards.add(card);
    }

    private void initHelpItem5() {
        Card card = new Card(getBaseContext());
        CardHeader cardHeader = new CardHeader(getBaseContext());
        cardHeader.setTitle("How do I activate PhoneBuddy?");
        cardHeader.setButtonExpandVisible(true);
        card.addCardHeader(cardHeader);
        CardExpand cardExpand = new CardExpand(getBaseContext());
        cardExpand.setTitle("There is a toggle button on the top bar where you can activate and deactivate the service.");
        card.addCardExpand(cardExpand);
        cards.add(card);
    }

    private void initHelpItem6() {
        Card card = new Card(getBaseContext());
        CardHeader cardHeader = new CardHeader(getBaseContext());
        cardHeader.setTitle("Why am I not receiving notifications on my buddy phone?");
        cardHeader.setButtonExpandVisible(true);
        card.addCardHeader(cardHeader);
        CardExpand cardExpand = new CardExpand(getBaseContext());
        cardExpand.setTitle("You need to enable Notification Access for PhoneBuddy. This can be accessed from Settings > Sound & notification > Notification access. For detailed instructions, check out our primer at http://tinyurl.com/PhoneBuddyPrimer.");
        card.addCardExpand(cardExpand);
        cards.add(card);
    }

    private void renderCards() {
        CardArrayRecyclerViewAdapter cardArrayRecyclerViewAdapter = new CardArrayRecyclerViewAdapter(getBaseContext(), cards);

        CardRecyclerView cardRecyclerView = (CardRecyclerView) findViewById(R.id.getting_started_recyclerview);
        cardRecyclerView.setHasFixedSize(false);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        if (cardRecyclerView != null) {
            cardRecyclerView.setAdapter(cardArrayRecyclerViewAdapter);
        }
    }
}
