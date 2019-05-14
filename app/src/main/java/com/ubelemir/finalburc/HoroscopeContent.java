package com.ubelemir.finalburc;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HoroscopeContent extends AppCompatActivity {
    private Horoscope activeHoroscope;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horoscope_content);
        Intent horoscopeContent = getIntent();
        String hrKey = horoscopeContent.getStringExtra("horoscopeKey");
        activeHoroscope = Horoscope.horoscopehMap.get(hrKey);

        TabLayout timeTabs = (TabLayout) findViewById(R.id.controlTabs);
        LinearLayout contentHeader = (LinearLayout) findViewById(R.id.horoscopeContentHeader);
        ImageView headerImage = (ImageView) findViewById(R.id.horoscopeContentHeaderImage);
        TextView headerName = (TextView) findViewById(R.id.horoscopeContentHeaderName);
        FABController fabController = new FABController(this);
        fabController.setColor(getColor(activeHoroscope.color));
        ViewPager horoscopeContentPager = findViewById(R.id.horoscopeContentPager);
        HoroscopeContentPager pager = new HoroscopeContentPager(getSupportFragmentManager());
        pager.setHoroscopeKey(hrKey);
        horoscopeContentPager.setAdapter(pager);
        horoscopeContentPager.setOffscreenPageLimit(1);
        timeTabs.setupWithViewPager(horoscopeContentPager);
        contentHeader.setBackground(getDrawable(activeHoroscope.bgPic));
        headerImage.setImageResource(activeHoroscope.iconID);
        headerName.setText(activeHoroscope.name);
        timeTabs.setBackgroundColor(getColor(activeHoroscope.color));



    }
}
