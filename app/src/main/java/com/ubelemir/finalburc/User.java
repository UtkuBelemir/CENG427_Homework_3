package com.ubelemir.finalburc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;

public class User {
    private static User ins;
    public Integer userID;
    public String email;
    public String name;
    public String lastname;
    public ArrayList<Friends> friends = new ArrayList<Friends>();
    public String horoscope;
    public String password;
    public BigInteger birth_date;

    public static User getIns() {
        if (ins == null) {
            ins = new User();
        }
        return ins;
    }

    public User() {
        ins = this;
    }

    public User(Integer userID, String email, String name, String lastname, String horoscope, String password, BigInteger birth_date) {
        this.userID = userID;
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.horoscope = horoscope;
        this.password = password;
        this.birth_date = birth_date;
        ins = this;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        ins = this;
    }

    public JSONObject userToObject() {
        JSONObject tempJSON = new JSONObject();
        try {
            if (this.userID != null) {
                tempJSON.put("user_id", this.userID);
            }
            if (this.email != null) {
                tempJSON.put("email", this.email);
            }
            if (this.name != null) {
                tempJSON.put("name", this.name);
            }
            if (this.lastname != null) {
                tempJSON.put("lastname", this.lastname);
            }
            if (this.friends != null) {
                JSONArray tempArr = new JSONArray();
                for (Friends friend : friends) {
                    tempArr.put(friend.friendToObject());
                }
                if (tempArr.length() > 0) {
                    tempJSON.put("friends", tempArr);
                }
            }
            if (this.horoscope != null) {
                tempJSON.put("horoscope", this.horoscope);
            }
            if (this.password != null) {
                tempJSON.put("password", this.password);
            }
            if (this.birth_date != null) {
                tempJSON.put("birth_date", this.birth_date);
            }
        } catch (JSONException err) {
            err.printStackTrace();
        }
        return tempJSON;
    }

    public static void initializeUser(int userID, String email, String name, String lastname, BigInteger birth_date, String horoscope, JSONArray friends) {
        User.getIns().userID = userID;
        User.getIns().email = email;
        User.getIns().name = name;
        User.getIns().lastname = lastname;
        User.getIns().birth_date = birth_date;
        User.getIns().horoscope = horoscope;
        try {
            if (friends != null) {
                User.getIns().friends = new ArrayList<Friends>();
                for (int i = 0; i < friends.length(); i++) {
                    JSONObject tempFriend = friends.getJSONObject(i);
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
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}