package com.ubelemir.finalburc;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        TextView friendHeaderName = (TextView) findViewById(R.id.friendHeaderName);
        TextView friendHeaderEmail = (TextView) findViewById(R.id.friendHeaderEmail);
        TextView friendHeaderHoroscope = (TextView) findViewById(R.id.friendHeaderHoroscope);
        User activeUser = User.getIns();
        Horoscope userHoroscope = Horoscope.horoscopehMap.get(activeUser.horoscope);
        friendHeaderName.setText(activeUser.name+" "+activeUser.lastname);
        friendHeaderEmail.setText(activeUser.email);
        friendHeaderHoroscope.setText(Horoscope.horoscopehMap.get(activeUser.horoscope).name);
        friendHeaderHoroscope.setTextColor(getColor(userHoroscope.color));

        FABController fabController = new FABController(this);
        fabController.hideButton("friends");
        TabLayout friendTabs = (TabLayout) findViewById(R.id.friendTabs);
        ViewPager friendPager = findViewById(R.id.friendTabPager);
        FriendPager pager = new FriendPager(getSupportFragmentManager());
        friendPager.setAdapter(pager);
        friendPager.setOffscreenPageLimit(1);
        friendTabs.setupWithViewPager(friendPager);

    }
}
