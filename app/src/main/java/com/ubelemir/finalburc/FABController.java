package com.ubelemir.finalburc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class FABController {
    Activity parentActivity;
    FloatingActionMenu fabMenu;
    FloatingActionButton friendsButton;
    FloatingActionButton backButton;
    FloatingActionButton logoutButton;

    public FABController(Activity parentActivity) {
        this.parentActivity = parentActivity;
        fabMenu = (FloatingActionMenu) this.parentActivity.findViewById(R.id.fab_menu);
        friendsButton = (FloatingActionButton) this.parentActivity.findViewById(R.id.fab_friends);
        backButton = (FloatingActionButton) this.parentActivity.findViewById(R.id.fab_back);
        logoutButton = (FloatingActionButton) this.parentActivity.findViewById(R.id.fab_logout);
        backButton.setOnClickListener(onGoBackClick);
        friendsButton.setOnClickListener(onFriendsClick);
        logoutButton.setOnClickListener(onLogoutClick);

    }

    public View.OnClickListener onGoBackClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fabMenu.close(true);
            parentActivity.onBackPressed();
        }
    };
    public View.OnClickListener onFriendsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fabMenu.close(true);
            if (!parentActivity.getClass().equals(FriendsActivity.class)) {
                Intent nextIntent = new Intent(parentActivity, FriendsActivity.class);
                parentActivity.startActivity(nextIntent);
            }
        }
    };
    public View.OnClickListener onLogoutClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fabMenu.close(true);
            SharedPreferences cookieManager = parentActivity.getSharedPreferences("finalBurc", Context.MODE_PRIVATE);
            cookieManager.edit().putInt("userID",-1).apply();
            Intent loginIntent = new Intent(parentActivity,LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            parentActivity.startActivity(loginIntent);
            parentActivity.finish();
        }
    };
    public void hideButton(String buttonName) {
        switch (buttonName) {
            case "friends":
                friendsButton.setVisibility(View.GONE);
                break;
            case "back":
                backButton.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    public void showButton(String buttonName) {
        switch (buttonName) {
            case "friends":
                friendsButton.setVisibility(View.VISIBLE);
                break;
            case "back":
                backButton.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public void setColor(int colorCode) {
        fabMenu.setMenuButtonColorNormal(colorCode);
        fabMenu.setMenuButtonColorPressed(colorCode);

        friendsButton.setColorNormal(colorCode);
        friendsButton.setColorPressed(colorCode);

        backButton.setColorNormal(colorCode);
        backButton.setColorPressed(colorCode);

        logoutButton.setColorNormal(colorCode);
        logoutButton.setColorPressed(colorCode);
    }
}
