package com.ubelemir.finalburc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class FriendPager extends android.support.v4.app.FragmentPagerAdapter {

    public FriendPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                FriendListFragment friendListFragment = new FriendListFragment();
                return friendListFragment;
            case 1:
                FriendFormFragment friendFormFragment = new FriendFormFragment();
                return friendFormFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Friend List";
            case 1:
                return "Add Friend";
            default:
                return "";
        }
    }
}
