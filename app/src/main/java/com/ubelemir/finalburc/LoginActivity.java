package com.ubelemir.finalburc;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences cookieManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Horoscope.initializeHoroscopes();
        setContentView(R.layout.start_screen);
        cookieManager = getSharedPreferences("finalBurc", MODE_PRIVATE);
        int userID = cookieManager.getInt("userID",-1);
        if (userID > 0) {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.get(Utils.hostURL+"/user/"+userID, getUserProfileDetail);
            return;
        }
        setContentView(R.layout.activity_login);
        ViewPager loginPager = findViewById(R.id.accountPager);
        loginPager.setAdapter(new LoginPager(getSupportFragmentManager()));
        loginPager.setOffscreenPageLimit(1);
    }
    public AsyncHttpResponseHandler getUserProfileDetail = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {
                JSONObject resp = new JSONObject(new String(responseBody));
                JSONObject respData = resp.getJSONObject("data");
                JSONArray respFriends = null;
                if (!respData.isNull("friends")) {
                    respFriends = respData.getJSONArray("friends");
                }
                User.initializeUser(
                        respData.getInt("user_id"),
                        respData.getString("email"),
                        respData.getString("name"),
                        respData.getString("lastname"),
                        BigInteger.valueOf(respData.getInt("birth_date")),
                        respData.getString("horoscope").toLowerCase(),
                        respFriends
                );
                Intent hrList = new Intent(getApplicationContext(),HoroscopeListActivity.class);
                startActivity(hrList);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    };
}
