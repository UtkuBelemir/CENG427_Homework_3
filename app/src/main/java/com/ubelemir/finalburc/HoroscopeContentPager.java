package com.ubelemir.finalburc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class HoroscopeContentPager extends android.support.v4.app.FragmentPagerAdapter {
    String horoscopeKey;
    public HoroscopeContentPager(FragmentManager fm){
        super(fm);
    }
    public void setHoroscopeKey(String key) {
        this.horoscopeKey = key;
    }
    @Override
    public Fragment getItem(int i) {
        HoroscopeDetailFragment horoscopeDetailFragment = new HoroscopeDetailFragment();
        Bundle bundle = new Bundle();
        switch (i){
            case 0:
                bundle.putString("TimeRange","daily");
                break;
            case 1:
                bundle.putString("TimeRange","weekly");
                break;
            case 2:
                bundle.putString("TimeRange","monthly");
                break;
            case 3:
                bundle.putString("TimeRange","yearly");
                break;
            default:
                bundle.putString("TimeRange","daily");
                break;
        }
        bundle.putString("horoscopeKey",this.horoscopeKey);
        horoscopeDetailFragment.setArguments(bundle);
        return horoscopeDetailFragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Daily";
            case 1:
                return "Weekly";
            case 2:
                return "Monthly";
            case 3:
                return "Yearly";
            default:
                return "";
        }
    }
}
