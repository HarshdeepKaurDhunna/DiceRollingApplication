package com.example.dicerollingapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> diceList;
    ArrayList<Integer> diceIconList;
    LayoutInflater layoutInflater;

    public CustomAdapter(Context context, ArrayList<String> diceList, ArrayList<Integer> diceIconList) {
        this.context = context;
        this.diceList = diceList;
        this.diceIconList = diceIconList;
        this.layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return this.diceList.size();
    }

    @Override
    public Object getItem(int i) {
        return  this.diceList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view  = layoutInflater.inflate(R.layout.layout, null);
        TextView contactText = view.findViewById(R.id.diceType);
        ImageView contactIcons  = view.findViewById(R.id.diceIcon);
        contactText.setText(diceList.get(i));
        contactIcons.setImageResource(diceIconList.get(i));
        return view;
    }


}
