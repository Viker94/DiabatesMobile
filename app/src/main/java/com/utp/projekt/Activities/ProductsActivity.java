package com.utp.projekt.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.utp.projekt.Entities.Products;
import com.utp.projekt.R;
import com.utp.projekt.Utils.RowAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;

public class ProductsActivity extends Activity{

    private Button fruit;
    private Button vege;
    private Button dairy;
    private Button bread;
    private Button meat;
    private Button cereal;
    private Button sweats;
    private Button other;
    private ListView list;
    private ArrayAdapter<Products> adapter;
    private static ArrayList<Products> products = new ArrayList<>();
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        fruit = (Button) findViewById(R.id.fruit);
        vege = (Button) findViewById(R.id.vege);
        dairy = (Button) findViewById(R.id.dairy);
        bread = (Button) findViewById(R.id.bread);
        meat = (Button) findViewById(R.id.meat);
        cereal = (Button) findViewById(R.id.cereal);
        sweats = (Button) findViewById(R.id.sweat);
        other = (Button) findViewById(R.id.other);
        list = (ListView) findViewById(R.id.list);
        search = (EditText) findViewById(R.id.search);
        final ArrayList<Products> products = getIntent().getParcelableArrayListExtra("TAB");
        adapter = new RowAdapter(this, R.layout.products_rows, products);
        list.setAdapter(adapter);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void fruit(View view){
        adapter.getFilter().filter("category0");
    }
    public void vege(View view){
        adapter.getFilter().filter("category1");
    }
    public void dairy(View view){
        adapter.getFilter().filter("category2");
    }
    public void bread(View view){
        adapter.getFilter().filter("category3");
    }
    public void meat(View view){
        adapter.getFilter().filter("category4");
    }
    public void cereal(View view){
        adapter.getFilter().filter("category5");
    }
    public void sweats(View view){
        adapter.getFilter().filter("category6");
    }
    public void other(View view){
        adapter.getFilter().filter("category7");
    }
}
