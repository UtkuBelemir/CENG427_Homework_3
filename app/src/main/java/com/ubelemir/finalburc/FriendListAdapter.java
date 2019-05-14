package com.ubelemir.finalburc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendListAdapter extends BaseAdapter {

    Context context;
    Friends[] data;
    private LayoutInflater inflater;

    public FriendListAdapter(Context context, Friends[] data) {
        this.context = context;
        this.data = data;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
    }

    @Override
    public Friends getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.friend_list_item, null);
        TextView friendName = (TextView) vi.findViewById(R.id.friend_name);
        TextView friendHoroscope = (TextView) vi.findViewById(R.id.friend_horoscope);
        Friends currentItem = getItem(position);
        friendName.setText(currentItem.name + " " + currentItem.lastname);
        friendHoroscope.setText(currentItem.horoscope);

        return vi;
    }
}