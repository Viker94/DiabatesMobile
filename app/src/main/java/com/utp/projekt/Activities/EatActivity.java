package com.utp.projekt.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.utp.projekt.Controller.Controller;
import com.utp.projekt.Entities.Consumption;
import com.utp.projekt.Entities.Products;
import com.utp.projekt.Entities.User;
import com.utp.projekt.R;
import com.utp.projekt.Utils.MyAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class EatActivity extends Activity{

    static HashMap<Products, Integer> map = new HashMap<>();
    JSONArray array;
    ListView list;
    TextView textwoda;
    TextView textpotas;
    TextView textsod;
    TextView brak;
    double woda=0;
    double potas=0;
    double sod=0;
    private BaseAdapter adapter;
    User user;

    private double p=0;
    private double w=0;
    private double s=0;
    private int ilosc=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);

        textwoda = (TextView) findViewById(R.id.textwoda);
        textpotas = (TextView) findViewById(R.id.textpotas);
        textsod = (TextView) findViewById(R.id.textsod);
        brak = (TextView) findViewById(R.id.brak);


        list = (ListView) findViewById(R.id.list);
        adapter = new MyAdapter(map, getLayoutInflater());
        list.setAdapter(adapter);
    }

    public void save(View view){
        user = getIntent().getParcelableExtra("USER");
        if(map.size()>0){
            for ( Products key : map.keySet() ) {
                p=user.getPotassium();w=user.getWater();s=user.getSodium();
                p+=key.getPotassium()*map.get(key);
                
                w+=key.getWater()*map.get(key);
                s+=key.getSodium()*map.get(key);

                final String[] params = {user.getId().toString(),key.getId().toString(),map.get(key).toString()};
                Controller.callServicAsync("consumption", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        map = new HashMap<>();
                        list = (ListView) findViewById(R.id.list);
                        adapter = new MyAdapter(map, getLayoutInflater());
                        list.setAdapter(adapter);
                        brak.setText("Brak danych");
                        Toast.makeText(getApplicationContext(), "Zapisano posiłek", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Controller.failure(statusCode, getApplicationContext());
                    }
                });
            }

        }



        String[] params = {user.getId().toString(), p + "", w + "", s + ""};
        Controller.callServicAsync("userSubs", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Controller.failure(statusCode, getApplicationContext());
            }
        });
        MainActivity.user.setPotassium(p);
        MainActivity.user.setWater(w);
        MainActivity.user.setSodium(s);
    }

    public void add(View view){
        Controller.callServiceJSON("products", new String[0], new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Intent intent = new Intent(EatActivity.this, ProductsActivity.class);
                Gson gson = new Gson();
                Products[] tab = gson.fromJson(response.toString(), Products[].class);
                ArrayList<Products> list = new ArrayList<Products>(Arrays.asList(tab));
                intent.putExtra("TAB", list);
                startActivityForResult(intent,1);
            }
        });
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        if(map.size()>0){
            woda=0;
            sod=0;
            potas=0;
            for ( Products key : map.keySet() ) {
                woda+=key.getWater()*map.get(key);
                sod+=key.getSodium()*map.get(key);
                potas+=key.getPotassium()*map.get(key);
                Log.i(">>>>>>>>>>>>.", key.getId().toString() + " " + key.getName() + " " + map.get(key));
            }
            textwoda.setText(String.format( "%.2f", woda )+"");
            textsod.setText(String.format( "%.2f", sod )+"");
            textpotas.setText(String.format( "%.2f", potas )+"");
            brak.setText(" ");
        }

        list = (ListView) findViewById(R.id.list);
        adapter = new MyAdapter(map, getLayoutInflater());
        list.setAdapter(adapter);




        super.onResume();
        //Refresh your stuff here
    }
}
