package com.utp.projekt.Activities;

import android.app.Activity;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.utp.projekt.Controller.Controller;
import com.utp.projekt.Entities.User;
import com.utp.projekt.R;
import com.utp.projekt.Utils.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.utp.projekt.Activities.MainActivity.user;
import static com.utp.projekt.R.id.pager;

/*
Klasa tworząca fragmety ekranu. Umożliwia to przesuwanie w lewo i prawo ekranu.
 */

public class MainFragmentActivity extends AppCompatActivity{

    private PagerAdapter adapter;

    private ListView drawerList;
    private ArrayAdapter<String> dAdapter;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.viewpager);
        List<Fragment> fragments = new ArrayList<>(); //lista fragmentów
        fragments.add(Fragment.instantiate(this, CalendarActivity.class.getName())); //dodanie 3 fragmentów
        fragments.add(Fragment.instantiate(this, MainActivity.class.getName()));
        fragments.add(Fragment.instantiate(this, GraphActivity.class.getName()));
        adapter = new PagerAdapter(super.getSupportFragmentManager(), fragments); //utworzenie adaptera z listą fragmentów
        final ViewPager pager = (ViewPager) findViewById(R.id.pager); //obiekt obsługujący fragmenty
        pager.setAdapter(adapter);
        pager.setCurrentItem(1); //ustawienie na środkowy (1) fragment, daje to złudzenie, że jeden ekran jest na lewo drugi na prawo.
        drawerList = (ListView) findViewById(R.id.navList);
        String[] elements = {"Strona główna", "Kalendarz", "Wykresy", "Reset wartości"};
        dAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elements);
        drawerList.setAdapter(dAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());


        builder = new AlertDialog.Builder(this);

        builder.setTitle("Resetowanie wartości");
        builder.setMessage("Czy na pewno chcesz zresetować wartości?");

        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                String[] params = {String.valueOf(user.getId())};


                Controller.callServicAsync("userReset", params, new AsyncHttpResponseHandler() { //zapisanie limitów do bazy
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(getApplicationContext(), "Zresetowano wartości", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Controller.failure(statusCode, getApplicationContext());
                    }
                });

                dialog.dismiss();
            }

        });

        builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });



        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setTitle("Panel nawigacyjny");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                switch (pager.getCurrentItem()){
                    case 0:
                        setTitle("Kalendarz");
                        break;
                    case 1:
                        setTitle("Strona główna");
                        break;
                    case 2:
                        setTitle("Wykres");
                        break;
                }
                invalidateOptionsMenu();
            }
        };

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (pager.getCurrentItem()){
                    case 0:
                        setTitle("Kalendarz");
                        break;
                    case 1:
                        setTitle("Strona główna");
                        break;
                    case 2:
                        setTitle("Wykres");
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final ViewPager pager = (ViewPager) findViewById(R.id.pager); //obiekt obsługujący fragmenty
            pager.setAdapter(adapter);

            switch (position) {
                case 0:
                    pager.setCurrentItem(1);
                    break;//ustawienie na środkowy (1) fragment, daje to złudzenie, że jeden ekran jest na lewo drugi na prawo.
                case 1:
                    pager.setCurrentItem(0);
                    break;
                case 2:
                    pager.setCurrentItem(2);
                    break;
                case 3:
                    resetDialog();
                    break;
            }
            drawerList.setItemChecked(position, true);
            drawerLayout.closeDrawer(drawerList);

        }

        private void resetDialog() {
            AlertDialog alert = builder.create();
            alert.show();

        }


    }

}
