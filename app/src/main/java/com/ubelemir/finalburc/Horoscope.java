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
    public static void initializeHoroscopes() {
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
    }
}
