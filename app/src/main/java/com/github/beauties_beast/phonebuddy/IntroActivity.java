package com.github.beauties_beast.phonebuddy;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {
    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(AppIntroFragment.newInstance(
                "Welcome to PhoneBuddy",
                "Let's get started.",
                R.drawable.logo_white,
                Color.parseColor("#FF5722")
        ));

        addSlide(AppIntroFragment.newInstance(
                "First, we'll need to access to your phone's SMS, contacts, phone, and location.",
                "We'll need these to pair your smartphone with your buddy phone.",
                R.drawable.logo_nexus,
                Color.parseColor("#FF5722")
        ));

        addSlide(AppIntroFragment.newInstance(
                "Next, we'll need notification access.",
                "We'll need this to forward push notifications to your buddy phone.",
                R.drawable.logo_nexus,
                Color.parseColor("#FF5722")
        ));

        addSlide(AppIntroFragment.newInstance(
                "Great! Let's get started!",
                "Hit Done to add your first buddy phone!",
                R.drawable.logo_nexus,
                Color.parseColor("#FF5722")
        ));

        askForPermissions(new String[]{
                Manifest.permission.BROADCAST_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }, 2);

        askForPermissions(new String[]{
                Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE,
        }, 3);

        showStatusBar(true);
        showSkipButton(false);
    }

    @Override
    public void onSkipPressed() {

    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        Intent intent = new Intent();
        intent.setClass(getBaseContext(), ManageBuddyPhonesActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSlideChanged() {

    }
}
