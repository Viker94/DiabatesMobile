package com.utp.projekt.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.utp.projekt.Entities.Products;
import com.utp.projekt.R;
import com.utp.projekt.Utils.RowAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
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
    HashMap<Products, Integer> map = new HashMap<>();

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
        final ArrayList<Products> products = getIntent().getParcelableArrayListExtra("TAB"); //zczytanie listy produktów
        adapter = new RowAdapter(this, R.layout.products_rows, products);
        list.setAdapter(adapter);
        search.addTextChangedListener(new TextWatcher() { //w momencie kiedy zmienia się zawartość okienka do wyszukiwania, zmienia się filtrowanie listy
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
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { //ustawienie możliwości klikania w obiekty listy i wywoływanie okienka
                final Products p = (Products) adapterView.getItemAtPosition(i);
                AlertDialog.Builder alert = new AlertDialog.Builder(ProductsActivity.this);
                alert.setTitle("Wybierz ilość");
                final EditText input = new EditText(ProductsActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(EatActivity.map.containsKey(p)){
                            EatActivity.map.put(p, EatActivity.map.get(p) + Integer.parseInt(input.getText().toString()));
                            Log.i(">>>>>>>>","Poszło");
                        } else {
                            Log.i(">>>>>>>>>>>>>>>>>>>>>.","Znowu nie działa");
                            EatActivity.map.put(p, Integer.parseInt(input.getText().toString()));
                        }
                    }
                });
                alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
            }
        });
        Log.i("asd", map.toString());
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
