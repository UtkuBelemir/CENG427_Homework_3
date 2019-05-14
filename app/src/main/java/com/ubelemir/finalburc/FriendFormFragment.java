package com.ubelemir.finalburc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFormFragment extends Fragment {

    TextView friendFormName;
    TextView friendFormLastname;
    TextView friendBirthDay;
    TextView friendBirthMonth;
    ViewPager friendPager;
    ListView friendList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_friend_form, container, false);
        friendFormName = (TextView) mainView.findViewById(R.id.friendFormName);
        friendFormLastname = (TextView) mainView.findViewById(R.id.friendFormLastname);
        friendBirthDay = (TextView) mainView.findViewById(R.id.friendBirthDay);
        friendBirthMonth = (TextView) mainView.findViewById(R.id.friendBirthMonth);
        TextView friendFormButton = (TextView) mainView.findViewById(R.id.friendFormButton);
        friendFormButton.setOnClickListener(formButtonClick);
        friendPager = (ViewPager) getActivity().findViewById(R.id.friendTabPager);
        friendList = (ListView) getActivity().findViewById(R.id.friendList);
        return mainView;
    }

    public View.OnClickListener formButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar tempDate = Calendar.getInstance();
            User activeUser = User.getIns();
            tempDate.set(2019, Integer.parseInt(friendBirthDay.getText().toString()) - 1, Integer.parseInt(friendBirthMonth.getText().toString()));
            Friends tempFriend = new Friends(
                    friendFormName.getText().toString(),
                    friendFormLastname.getText().toString(),
                    null,
                    BigInteger.valueOf(tempDate.getTimeInMillis() / 1000));
            JSONArray friendsAll = new JSONArray();
            activeUser.friends.add(tempFriend);
            for (int i = 0;i<activeUser.friends.size();i++){
                friendsAll.put(activeUser.friends.get(i).friendToObject());
            }
            try {
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                StringEntity entity = new StringEntity(activeUser.userToObject().toString());
                Log.i("POSTDAT",activeUser.userToObject().toString());
                asyncHttpClient.post(getContext(),Utils.hostURL+"/user",entity,"application/json",formPostHandler);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    };
    public AsyncHttpResponseHandler formPostHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {
                JSONObject resp = new JSONObject(new String(responseBody));
                JSONObject respData = resp.getJSONObject("data");
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
                friendList.invalidateViews();
                friendList.refreshDrawableState();
                friendPager.setCurrentItem(0);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.i("EKLENEMEDI",new String(responseBody));
        }
    };
}
