package com.utp.projekt.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.utp.projekt.Controller.Controller;
import com.utp.projekt.Entities.Consumption;
import com.utp.projekt.Entities.Products;
import com.utp.projekt.Entities.User;
import com.utp.projekt.R;
import com.utp.projekt.Utils.Helper;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Marcin on 29.11.2016.
 */

public class MainActivity extends Fragment {

    private TextView name; //przywitanie
    private static ProgressBar pp; //progress bary dla potasu, wody i sodu
    private static ProgressBar pw;
    private static ProgressBar ps;
    private static TextView tp; //textview dla liczby obok progress bara
    private static TextView tw;
    private static TextView ts;
    private Space spacer;
    private Button buttonLimit; //przycisk limitów
    private Button eat; //przycisk do posiłków
    private Button history; //przycisk historii
    private Button bWater;

    private double water;
    private int sub=20;  ///wartość o ile odejmujemy
    public static User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Override
    public void onResume() {
        refreshLimits();
        super.onResume();
        this.onCreate(null);
    }


    public void startLimitActivity() //metoda startująca cześć z limitami
    {
        Intent intent = new Intent(getActivity(), LimitActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void openEat(){ //posiłki
        Intent intent = new Intent(getActivity(), EatActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void history(){ //historia
        Intent intent = new Intent(getActivity(), HistoryActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main2, container, false);
        user = getActivity().getIntent().getParcelableExtra("USER"); //zczytanie usera
        Log.i("MAIN", user.getConsumptions().get(0).toString());
        Log.i("MAIN", user.getNextVisit().toString());
        name = (TextView) rootView.findViewById(R.id.name);
        name.setText("Witaj: " + user.getFirstName() + " " + user.getLastName()); //przypisanie tekstu
        buttonLimit = (Button) rootView.findViewById(R.id.limit);
        eat = (Button) rootView.findViewById(R.id.eat);
        history = (Button) rootView.findViewById(R.id.history);
        bWater = (Button) rootView.findViewById(R.id.bWater);
        pp = (ProgressBar) rootView.findViewById(R.id.pPotassium);
        pw = (ProgressBar) rootView.findViewById(R.id.pWater);
        ps = (ProgressBar) rootView.findViewById(R.id.pSodium);
        tp = (TextView) rootView.findViewById(R.id.ppPotassium);
        tw = (TextView) rootView.findViewById(R.id.ppWater);
        ts = (TextView) rootView.findViewById(R.id.ppSodium);
        pp.setProgressTintList(ColorStateList.valueOf(Helper.POTASSIUM_COLOR)); //ustawianie kolorów dla pasków
        pw.setProgressTintList(ColorStateList.valueOf(Helper.WATER_COLOR));
        ps.setProgressTintList(ColorStateList.valueOf(Helper.SODIUM_COLOR));

        refreshLimits();

        bWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                water= (int) user.getWater();
                if(water<sub) water=0;
                else
                    water-=sub;
                String[] params = {user.getId().toString(), water + ""};
                Controller.callServicAsync("userWater", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(getContext(), "Usnięto część wody", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Controller.failure(statusCode, getContext());
                    }
                });
                user.setWater(water);
                refreshLimits();
            }
        });

        buttonLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLimitActivity();
            }
        }); //przypisanie funkcji do przycisków
        eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEat();
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                history();
            }
        });
        return rootView;
    }



    public static void refreshLimits(){
        pp.setProgress((int)((user.getPotassium()/user.getLimitPotassium())*100)); //obliczanie zawartości w % dla substancji
        pw.setProgress((int)((user.getWater()/user.getLimitWater())*100));
        ps.setProgress((int)((user.getSodium()/user.getLimitSodium())*100));
        tp.setText((int)((user.getPotassium()/user.getLimitPotassium())*100) + "%"); //ustawienie tekstu
        tw.setText((int)((user.getWater()/user.getLimitWater())*100) + "%");
        ts.setText((int)((user.getSodium()/user.getLimitSodium())*100) + "%");}
}
