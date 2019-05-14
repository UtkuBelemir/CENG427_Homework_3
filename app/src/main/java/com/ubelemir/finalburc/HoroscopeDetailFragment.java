package com.ubelemir.finalburc;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HoroscopeDetailFragment extends Fragment {
    Horoscope activeHoroscope;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_horoscope_detail, container, false);
        String timeRange = getArguments().getString("TimeRange");
        String horoscopeKey = getArguments().getString("horoscopeKey");
        activeHoroscope = Horoscope.horoscopehMap.get(horoscopeKey);
        TextView contentText = (TextView) fragmentView.findViewById(R.id.contentText);
        try {
            contentText.setText("\t\t\t"+activeHoroscope.content.getString(timeRange));
        } catch (Exception err) {
            err.printStackTrace();
        }
        return fragmentView;
    }

}
