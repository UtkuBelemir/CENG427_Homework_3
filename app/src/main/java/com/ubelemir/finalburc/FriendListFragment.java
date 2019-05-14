package com.ubelemir.finalburc;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendListFragment extends Fragment {
    ListView friendList;
    TextView emptyPlaceholder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_friend_list, container, false);
        friendList = (ListView) mainView.findViewById(R.id.friendList);
        emptyPlaceholder = (TextView) mainView.findViewById(R.id.emptyPlaceholder);
        initializeFriendList();
        return mainView;
    }

    public void initializeFriendList(){
        User userIns = User.getIns();
        if (userIns.friends != null && userIns.friends.size() > 0) {
            Friends[] frArr = new Friends[userIns.friends.size()];
            for(int i = 0; i < userIns.friends.size(); i++) {
                frArr[i] = userIns.friends.get(i);
            }
            friendList.setAdapter(new FriendListAdapter(getContext(), frArr));
            friendList.setOnItemClickListener(friendClick);
            emptyPlaceholder.setVisibility(View.GONE);
            friendList.setVisibility(View.VISIBLE);
        } else {
            emptyPlaceholder.setVisibility(View.VISIBLE);
            friendList.setVisibility(View.GONE);
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser &&  friendList != null) {
            initializeFriendList();
        }
    }

    private AdapterView.OnItemClickListener friendClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Friends clickedFriend = (Friends) parent.getItemAtPosition(position);
            Intent horoscopeContent = new Intent(getActivity(),HoroscopeContent.class);
            horoscopeContent.putExtra("horoscopeKey", clickedFriend.horoscope.toLowerCase());
            startActivity(horoscopeContent);
        }
    };
}
