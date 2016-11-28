package com.utp.projekt.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.utp.projekt.Controller.Controller;
import com.utp.projekt.Entities.Products;
import com.utp.projekt.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class EatActivity extends Activity{

    JSONArray array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);
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
