package com.ubelemir.finalburc;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class LoginPager extends android.support.v4.app.FragmentPagerAdapter {
    public LoginPager(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0)
            return new LoginFragment();
        return new SignupFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Log In";
            case 1:
                return "Sign Up";
            default:
                return "";
        }
    }
}
