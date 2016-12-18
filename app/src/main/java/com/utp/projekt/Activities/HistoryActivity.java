package com.utp.projekt.Activities;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.utp.projekt.Entities.Consumption;
import com.utp.projekt.Entities.Products;
import com.utp.projekt.Entities.User;
import com.utp.projekt.R;
import com.utp.projekt.Utils.MyAdapter;
import com.utp.projekt.Utils.RowAdapter;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public class HistoryActivity extends Activity {

    private Spinner spinner;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        final User user = getIntent().getParcelableExtra("USER"); //zczytanie usera
        final Context context = this;
        spinner = (Spinner) findViewById(R.id.spinner); //lista rozwijana
        list = (ListView) findViewById(R.id.listProducts);
        List<Date> datesBefore = new ArrayList<>(); //lista z datami z bazy danych
        LinkedHashSet<String> datesStringSet = new LinkedHashSet<>();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        for(Consumption c : user.getConsumptions()){
            datesBefore.add(c.getDate()); //zczytanie dat z bazy
            datesStringSet.add(format.format(c.getDate())); //przerobienie ich na stringa
        }
        //wyrzucenie duplikatów dat
        for(int i = 0; i < datesBefore.size(); i++){
            for(int j = 0; j < datesBefore.size(); j++){
                if(i!=j){
                    Date date1 = datesBefore.get(j);
                    Date date2 = datesBefore.get(i);
                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal1.setTime(date1);
                    cal2.setTime(date2);
                    if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)){
                        datesBefore.remove(i);
                    }
                }
            }
        }
        final List<Date> dates = new ArrayList<>(datesBefore);
        Collections.sort(dates); //sortowanie od najmniejszej do największej a następnie odwrócenie
        Collections.reverse(dates);
        List<String> datesString = new ArrayList<>(datesStringSet);
        Log.i("D1", dates.toString());
        Log.i("D2", datesString.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, datesString); //przypisanie do adaptera
        spinner.setAdapter(adapter);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);
            popupWindow.setHeight(500); //określenie wielkości listy rozwijanej
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //cześć obsługująca klikanie w listę rozwijaną
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<Products, Integer> data = new HashMap<Products, Integer>();
                for(Consumption c : user.getConsumptions()){
                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal1.setTime(c.getDate());
                    cal2.setTime(dates.get(i));
                    if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)){
                        data.put(c.getProduct(), c.getAmount());
                    }
                }
                BaseAdapter adapter = new MyAdapter(data, getLayoutInflater());
                list.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
