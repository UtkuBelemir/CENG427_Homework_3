package com.ubelemir.finalburc;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

public class Horoscope implements Serializable {
    public static HashMap<String,Horoscope> horoscopehMap = new HashMap<String,Horoscope>();
    public String name;
    public String key;
    public int color;
    public int layoutID;
    public int iconID;
    public int bgPic;
    public JSONObject content;
    Horoscope(String name, int color,int layoutID,int iconID,int bgPic){
        this.name = name;
        this.color = color;
        this.layoutID = layoutID;
        this.iconID = iconID;
        this.bgPic = bgPic;
        this.key = name.toLowerCase();
    }
}
