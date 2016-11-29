package com.utp.projekt.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.utp.projekt.Entities.Consumption;
import com.utp.projekt.Entities.Products;
import com.utp.projekt.R;
import com.utp.projekt.Utils.RowAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends BaseAdapter {
    private HashMap<Products, Integer> mData = new HashMap<Products, Integer>();
    private Products[] mKeys;
    private LayoutInflater inflater;
    public MyAdapter(HashMap<Products, Integer> data,LayoutInflater inflater){
        mData  = data;
        mKeys = mData.keySet().toArray(new Products[data.size()]);
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(mKeys[position]);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }


    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        Products key = mKeys[pos];

        ConsumedHolder holder=null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.consumed_rows, parent, false);
            holder = new ConsumedHolder();

            holder.nazwa = (TextView) convertView.findViewById(R.id.nazwaList);
            holder.ammount = (TextView) convertView.findViewById(R.id.ammountList);
            convertView.setTag(holder);
        } else {
            holder = (ConsumedHolder)convertView.getTag();
        }
        Products tmp = key;
        holder.nazwa.setText(tmp.getName().toString());
        holder.ammount.setText(mData.get(key).toString());


        return convertView;
    }
}