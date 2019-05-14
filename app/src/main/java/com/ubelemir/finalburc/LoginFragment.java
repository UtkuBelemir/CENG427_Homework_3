package com.ubelemir.finalburc;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.math.BigInteger;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    public ViewPager accountPager;
    public EditText loginUsername;
    public EditText loginPassword;
    SharedPreferences cookieManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_login, container, false);
        cookieManager = getActivity().getSharedPreferences("finalBurc", Context.MODE_PRIVATE);
        accountPager = (ViewPager) getActivity().findViewById(R.id.accountPager);
        LinearLayout signUpButton = (LinearLayout) mainView.findViewById(R.id.signupButton);
        signUpButton.setOnClickListener(buttonClick);
        loginUsername = (EditText) mainView.findViewById(R.id.loginEmail);
        loginPassword = (EditText) mainView.findViewById(R.id.loginPassword);
        TextView signInButton = (TextView) mainView.findViewById(R.id.loginSignInButton);
        signInButton.setOnClickListener(signInButtonClick);
        return mainView;
    }
    public View.OnClickListener buttonClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            accountPager.setCurrentItem(1);
        }
    };
    public View.OnClickListener signInButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                JSONObject userData = new User(loginUsername.getText().toString(),loginPassword.getText().toString()).userToObject();
                AsyncHttpClient httpClient = new AsyncHttpClient();
                StringEntity entity = new StringEntity(userData.toString());
                httpClient.post(getContext(),Utils.hostURL+"/login",entity,"application/json",loginResponse);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    };
    public AsyncHttpResponseHandler loginResponse = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {
                JSONObject resp = new JSONObject(new String(responseBody));
                if (resp.getBoolean("success")) {
                    JSONObject respData = resp.getJSONObject("data");
                    cookieManager.edit().putInt("userID",respData.getInt("user_id")).apply();
                    User.getIns().userID = respData.getInt("user_id");
                    User.getIns().email = respData.getString("email");
                    User.getIns().name = respData.getString("name");
                    User.getIns().lastname = respData.getString("lastname");
                    User.getIns().birth_date = BigInteger.valueOf(respData.getInt("birth_date"));
                    User.getIns().horoscope = respData.getString("horoscope").toLowerCase();
                    if (!respData.isNull("friends")) {
                        JSONArray respFriends = respData.getJSONArray("friends");
                        User.getIns().friends = new ArrayList<Friends>();
                        for (int i = 0; i < respFriends.length(); i++) {
                            JSONObject tempFriend = respFriends.getJSONObject(i);
                            User.getIns().friends.add(
                                    new Friends(
                                            tempFriend.getString("name"),
                                            tempFriend.getString("lastname"),
                                            tempFriend.getString("horoscope"),
                                            BigInteger.valueOf(tempFriend.getInt("birth_date"))
                                    )
                            );
                        }
                    }
                    Toast.makeText(getContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                    Intent hrList = new Intent(getActivity(),HoroscopeListActivity.class);
                    startActivity(hrList);
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            try {
                Log.i("Signup resp fal",new String(responseBody));
                JSONObject resp = new JSONObject(new String(responseBody));
                Toast.makeText(getContext(),resp.get("message").toString(),Toast.LENGTH_SHORT).show();
            } catch (Exception err) {

            }
        }
    };
}
