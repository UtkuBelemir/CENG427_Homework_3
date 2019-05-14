package com.ubelemir.finalburc;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;

public class Friends {
    public String name;
    public String lastname;
    public String horoscope;
    public BigInteger birth_date;
    public Friends(String name, String lastname, String horoscope, BigInteger birth_date){
        this.name = name;
        this.lastname=lastname;
        this.horoscope=horoscope;
        this.birth_date=birth_date;
    }
    public JSONObject friendToObject() {
        JSONObject tempFriend = new JSONObject();
        try {
            if (this.name != null) {
                tempFriend.put("name",this.name);
            }
            if (this.lastname != null) {
                tempFriend.put("lastname",this.lastname);
            }
            if (this.horoscope != null) {
                tempFriend.put("horoscope",this.horoscope);
            }
            if (this.birth_date != null) {
                tempFriend.put("birth_date",this.birth_date);
            }
        } catch (JSONException err) {
            err.printStackTrace();
        }
        return tempFriend;
    }
}
