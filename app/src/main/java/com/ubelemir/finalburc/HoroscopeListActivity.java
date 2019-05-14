package com.ubelemir.finalburc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class HoroscopeListActivity extends AppCompatActivity {
    boolean isFABOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horoscope_list);
        Horoscope.horoscopehMap.put("capricorn",new Horoscope("Capricorn",R.color.horoscopeOrangeLight,R.id.hritem_capricorn,R.drawable.hricon_capricorn,R.drawable.hrbg_orange));
        Horoscope.horoscopehMap.put("aquarius",new Horoscope("Aquarius",R.color.horoscopeGreenLight,R.id.hritem_aquarius,R.drawable.hricon_aquarius,R.drawable.hrbg_green));
        Horoscope.horoscopehMap.put("pisces",new Horoscope("Pisces",R.color.horoscopeBlueLight,R.id.hritem_pisces,R.drawable.hricon_pisces,R.drawable.hrbg_blue));
        Horoscope.horoscopehMap.put("aries",new Horoscope("Aries",R.color.horoscopeRedLight,R.id.hritem_aries,R.drawable.hricon_aries,R.drawable.hrbg_red));
        Horoscope.horoscopehMap.put("taurus",new Horoscope("Taurus",R.color.horoscopeOrangeLight,R.id.hritem_taurus,R.drawable.hricon_taurus,R.drawable.hrbg_orange));
        Horoscope.horoscopehMap.put("gemini",new Horoscope("Gemini",R.color.horoscopeGreenLight,R.id.hritem_gemini,R.drawable.hricon_gemini,R.drawable.hrbg_green));
        Horoscope.horoscopehMap.put("cancer",new Horoscope("Cancer",R.color.horoscopeBlueLight,R.id.hritem_cancer,R.drawable.hricon_cancer,R.drawable.hrbg_blue));
        Horoscope.horoscopehMap.put("leo",new Horoscope("Leo",R.color.horoscopeRedLight,R.id.hritem_leo,R.drawable.hricon_leo,R.drawable.hrbg_red));
        Horoscope.horoscopehMap.put("virgo",new Horoscope("Virgo",R.color.horoscopeOrangeLight,R.id.hritem_virgo,R.drawable.hricon_virgo,R.drawable.hrbg_orange));
        Horoscope.horoscopehMap.put("libra",new Horoscope("Libra",R.color.horoscopeGreenLight,R.id.hritem_libra,R.drawable.hricon_libra,R.drawable.hrbg_green));
        Horoscope.horoscopehMap.put("scorpio",new Horoscope("Scorpio",R.color.horoscopeBlueLight,R.id.hritem_scorpio,R.drawable.hricon_scorpio,R.drawable.hrbg_blue));
        Horoscope.horoscopehMap.put("sagittarius",new Horoscope("Sagittarius",R.color.horoscopeRedLight,R.id.hritem_sagittarius,R.drawable.hricon_sagittarius,R.drawable.hrbg_red));
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
             //   Toast.makeText(getApplicationContext(),"Clicked id is"+itemName,Toast.LENGTH_SHORT).show();
            }
        };
    }
    private AsyncHttpResponseHandler horoscopeResponse = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {
                JSONObject resp = new JSONObject(new String(responseBody));
                Log.i("RESPOO",resp.toString());
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
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    };
}
