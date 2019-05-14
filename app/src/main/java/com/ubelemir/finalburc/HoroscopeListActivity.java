package com.ubelemir.finalburc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class HoroscopeListActivity extends AppCompatActivity {
    boolean isFABOpen = false;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horoscope_list);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Horoscope Content Loading...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(Utils.hostURL+"/horoscopes",horoscopeResponse);
        for (Map.Entry hrEntry : Horoscope.horoscopehMap.entrySet()) {
            Horoscope tempHr = (Horoscope) hrEntry.getValue();
            LinearLayout tempLayout = (LinearLayout) findViewById(tempHr.layoutID);
            tempLayout.setOnClickListener(hrItemClickListener(tempHr.key));
        }
        FABController fabController = new FABController(this);
        fabController.hideButton("back");
    }
    private View.OnClickListener hrItemClickListener(final String horoscopeKey){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent horoscopeContent = new Intent(getApplicationContext(),HoroscopeContent.class);
                horoscopeContent.putExtra("horoscopeKey", horoscopeKey);
                startActivity(horoscopeContent);
            }
        };
    }
    private AsyncHttpResponseHandler horoscopeResponse = new AsyncHttpResponseHandler() {
        @Override
        public void onStart() {
            dialog.show();
            super.onStart();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {
                JSONObject resp = new JSONObject(new String(responseBody));
                if (resp.getBoolean("success")) {
                    JSONObject respData = resp.getJSONObject("data");
                    for (Map.Entry hrEntry : Horoscope.horoscopehMap.entrySet()) {
                        Horoscope tempHr = (Horoscope) hrEntry.getValue();
                        tempHr.content = respData.getJSONObject(tempHr.key).getJSONObject("content");
                    }
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
            dialog.dismiss();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(getApplicationContext(),"Error while fetching horoscopes",Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    };
}
