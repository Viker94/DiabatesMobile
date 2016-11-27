package com.utp.projekt.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.utp.projekt.Entities.Products;
import com.utp.projekt.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin on 27.11.2016.
 */

public class RowAdapter extends ArrayAdapter<Products> implements Filterable{

    private Context context;
    private int resource;
    private List<Products> products;
    private List<Products> filtered;

    public RowAdapter(Context context, int resource, List<Products> objects) {
        super(context, resource, objects);
        this.context =context;
        this.resource = resource;
        this.products = objects;
        this.filtered = objects;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductHolder holder = null;
        if(convertView == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
            holder = new ProductHolder();
            holder.name = (TextView) convertView.findViewById(R.id.nameList);
            holder.pot = (TextView) convertView.findViewById(R.id.potassiumList);
            holder.water = (TextView) convertView.findViewById(R.id.waterList);
            holder.sod = (TextView) convertView.findViewById(R.id.sodiumList);
            holder.cat = (TextView) convertView.findViewById(R.id.categoryList);
            convertView.setTag(holder);
        } else {
            holder = (ProductHolder)convertView.getTag();
        }
        Products tmp = products.get(position);
        holder.name.setText(tmp.getName());
        holder.pot.setText(tmp.getPotassium()+"");
        holder.water.setText(tmp.getWater()+"");
        holder.sod.setText(tmp.getSodium()+"");
        switch (tmp.getCategory()){
            case Category.FRUIT:
                holder.cat.setText("Owoce");
                break;
            case Category.VEGETABLE:
                holder.cat.setText("Warzywa");
                break;
            case Category.DAIRY:
                holder.cat.setText("Nabiał");
                break;
            case Category.BREAD:
                holder.cat.setText("Pieczywo");
                break;
            case Category.MEAT:
                holder.cat.setText("Mięso");
                break;
            case Category.CEREAL:
                holder.cat.setText("Kasze");
                break;
            case Category.SWEAT:
                holder.cat.setText("Słodycze");
                break;
            case Category.OTHER:
                holder.cat.setText("Inne");
                break;
        }
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                if(charSequence == null || charSequence.length() == 0){
                    results.values = products;
                    results.count = products.size();
                } else {
                    if(charSequence.toString().startsWith("category")){
                        List<Products> nProducts = new ArrayList<>();
                        for (Products p : products) {
                            String cat = charSequence.toString().substring(8, charSequence.toString().length());
                            int category = Integer.parseInt(cat);
                            for(Products pr : products){
                                if(pr.getCategory() == category) nProducts.add(pr);
                            }
                        }
                        results.values = nProducts;
                        results.count = nProducts.size();
                    } else {
                        List<Products> nProducts = new ArrayList<>();
                        for (Products p : products) {
                            if (p.getName().toLowerCase().startsWith(charSequence.toString()))
                                nProducts.add(p);
                        }
                        results.values = nProducts;
                        results.count = nProducts.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if(filterResults.count == 0){
                    notifyDataSetInvalidated();
                } else {
                    products = (List<Products>) filterResults.values;
                    notifyDataSetChanged();
                }
            }
        };
        return filter;
    }

    @Override
    public int getCount() {
        return products.size();
    }
}
