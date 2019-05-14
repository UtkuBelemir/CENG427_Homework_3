package com.ubelemir.finalburc;


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
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    public ViewPager accountPager;
    private EditText signupEmail;
    private EditText signupPassword;
    private EditText signupName;
    private EditText signupLastname;
    private EditText signupBirthDay;
    private EditText signupBirthMonth;
    SharedPreferences cookieManager;
    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_signup, container, false);
        cookieManager = getActivity().getSharedPreferences("finalBurc", MODE_PRIVATE);
        accountPager = (ViewPager) getActivity().findViewById(R.id.accountPager);
        LinearLayout loginButton = (LinearLayout) mainView.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(loginButtonClick);
        signupEmail = (EditText) mainView.findViewById(R.id.signupEmail);
        signupPassword = (EditText) mainView.findViewById(R.id.signupPassword);
        signupName = (EditText) mainView.findViewById(R.id.signupName);
        signupLastname = (EditText) mainView.findViewById(R.id.signupLastname);
        signupBirthDay = (EditText) mainView.findViewById(R.id.signupBirthDay);
        signupBirthMonth = (EditText) mainView.findViewById(R.id.signupBirthMonth);
        TextView signupButton = (TextView) mainView.findViewById(R.id.signupSignUpButton);
        signupButton.setOnClickListener(signupButtonClick);
        return mainView;
    }

    public View.OnClickListener loginButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            accountPager.setCurrentItem(0);
        }
    };
    public View.OnClickListener signupButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar tempDate = Calendar.getInstance();
            tempDate.set(2019, Integer.parseInt(signupBirthMonth.getText().toString()) - 1, Integer.parseInt(signupBirthDay.getText().toString()));
            JSONObject userData = new User(
                    null,
                    signupEmail.getText().toString(),
                    signupName.getText().toString(),
                    signupLastname.getText().toString(),
                    null,
                    signupPassword.getText().toString(),
                    BigInteger.valueOf(tempDate.getTimeInMillis() / 1000)).userToObject();
            try {
                AsyncHttpClient httpClient = new AsyncHttpClient();
                StringEntity entity = new StringEntity(userData.toString());
                httpClient.post(getContext(),Utils.hostURL+"/user",entity,"application/json",signupResponse);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    };
    public AsyncHttpResponseHandler signupResponse = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {
                JSONObject resp = new JSONObject(new String(responseBody));
                if (resp.getBoolean("success")) {
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
                    cookieManager.edit().putInt("userID",respData.getInt("user_id")).apply();
                    Toast.makeText(getContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                    Intent hrListIntent = new Intent(getActivity(),HoroscopeListActivity.class);
                    hrListIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(hrListIntent);
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            try {
                JSONObject resp = new JSONObject(new String(responseBody));
                Toast.makeText(getContext(),resp.get("message").toString(),Toast.LENGTH_SHORT).show();
            } catch (Exception err) {

            }
        }
    };
}
