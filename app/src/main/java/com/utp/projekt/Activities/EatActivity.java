package com.utp.projekt.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.utp.projekt.Controller.Controller;
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
}
