package com.solar.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.solar.R;

import java.util.ArrayList;


public class StringArrayAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> list;
    LayoutInflater inflter;

    public StringArrayAdapter(Context applicationContext, ArrayList<String> list) {
        this.context = applicationContext;
        this.list = list;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_item, null);

        TextView textView = view.findViewById(R.id.tvSpinerItem);
        /*if (i == 0){
            textView.setTextColor(Color.WHITE);
        }*/
        textView.setText(list.get(i));
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = null;

        // If this is the initial dummy entry, make it hidden
        if (position == 0) {
            TextView tv = new TextView(context);
            tv.setHeight(0);
            tv.setVisibility(View.GONE);
            view = tv;
        } else {
            // Pass convertView as null to prevent reuse of special case views
            view = super.getDropDownView(position, null, parent);
        }

        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
        parent.setVerticalScrollBarEnabled(false);
        return view;
    }
}
